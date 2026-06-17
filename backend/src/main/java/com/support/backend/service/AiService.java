package com.support.backend.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class AiService {



    private final WebClient webClient;



    @Value("${ai.api.key}")
    private String apiKey;



    @Value("${ai.api.url}")
    private String apiUrl;



    @Value("${ai.model}")
    private String model;






    public AiService(){


        this.webClient =

                WebClient

                        .builder()

                        .build();


    }









    public String generateReply(

            String conversation

    ){



        try{



            Map<String,Object> request =

                    Map.of(


                            "model",

                            model,



                            "messages",

                            List.of(


                                    Map.of(

                                            "role",

                                            "system",


                                            "content",

                                            getSystemPrompt()

                                    ),




                                    Map.of(

                                            "role",

                                            "user",


                                            "content",

                                            conversation

                                    )


                            )


                    );









            Map response =


                    webClient

                            .post()

                            .uri(apiUrl)



                            .header(

                                    "Authorization",

                                    "Bearer " + apiKey

                            )



                            .header(

                                    "Content-Type",

                                    "application/json"

                            )



                            .bodyValue(

                                    request

                            )



                            .retrieve()



                            .bodyToMono(

                                    Map.class

                            )


                            .block();









            if(response == null){


                return fallback(

                        conversation

                );


            }








            List choices =

                    (List)

                            response.get(

                                    "choices"

                            );





            if(

                    choices == null

                    ||

                    choices.isEmpty()

            ){


                return fallback(

                        conversation

                );


            }









            Map choice =

                    (Map)

                            choices.get(0);




            Map message =

                    (Map)

                            choice.get(

                                    "message"

                            );







            String answer =

                    String.valueOf(

                            message.get(

                                    "content"

                            )

                    );






            return cleanAnswer(

                    answer

            );




        }


        catch(Exception e){



            return fallback(

                    conversation

            );


        }


    }














    private String getSystemPrompt(){



        return """

        You are ShopSphere AI customer support assistant.

        Your behavior:

        - Talk like a real human support agent.
        - Never say you are an AI model.
        - Remember previous conversation.
        - Never repeat same answers.
        - Understand customer emotion.
        - Give short chat style answers.
        - Ask only one question at a time.
        - Provide practical solutions.
        - If solved ask if anything else is needed.

        Company rules:

        Payment issue:
        collect transaction id, amount, payment date.

        Login issue:
        suggest password reset and verification.

        Delivery issue:
        ask order id and tracking details.

        Refund:
        explain refund checking process.

        Angry customer:
        apologize politely and continue helping.

        """;


    }













    private String cleanAnswer(

            String answer

    ){



        if(

                answer == null

                ||

                answer.isBlank()

        ){


            return "I understand your issue. Could you share more details so I can help you?";


        }





        return answer.trim();


    }















    private String fallback(

        String conversation

){



    String text =

            conversation == null

                    ?

                    ""

                    :

                    conversation.toLowerCase();




    String lastMessage =

            extractLastUserMessage(

                    text

            );









    // GREETING


    if(

            contains(

                    lastMessage,

                    "hi",

                    "hello",

                    "hey",

                    "hii"

            )

    ){


        return "Hello 👋 Welcome to ShopSphere support. Please tell me your issue and I will help you.";


    }








    // PAYMENT / ORDER FAILED FLOW


    if(

            contains(

                    text,

                    "payment",

                    "paid",

                    "money",

                    "deducted",

                    "transaction",

                    "upi",

                    "order failed",

                    "order not placed"

            )

    ){



        boolean transaction =

                contains(

                        text,

                        "transaction id",

                        "txn",

                        "utr"

                );



        boolean amount =

                contains(

                        text,

                        "amount",

                        "rs",

                        "₹"

                );



        boolean date =

                contains(

                        text,

                        "date"

                );




        if(!transaction){


            return "I can help verify your payment. Please share your transaction ID or UPI reference number.";


        }



        if(!amount){


            return "Thanks, I received your transaction details. Please share the deducted amount.";


        }



        if(!date){


            return "Almost done. Please share the payment date so our billing team can trace it.";


        }



        return "Thank you. Your payment details are collected successfully. I am forwarding them for verification and order update.";


    }











    // DELIVERY


    if(

            contains(

                    text,

                    "delivery",

                    "late",

                    "delay",

                    "not received",

                    "tracking",

                    "where is my order"

            )

    ){


        if(!contains(text,"order")){


            return "I will check your delivery status. Please share your order number.";


        }



        return "Thanks. I will check the shipment status and update you with the delivery information.";


    }









    // DAMAGED PRODUCT


    if(

            contains(

                    text,

                    "broken",

                    "damage",

                    "damaged",

                    "not working",

                    "sound",

                    "display",

                    "battery",

                    "defective",

                    "issue"

            )

    ){



        if(!contains(text,"order")){


            return "Sorry about the product issue. Please share your order number so I can check replacement or warranty options.";


        }




        return "Thank you. I have your product issue details. We will verify warranty/replacement eligibility.";


    }









    // RETURN


    if(

            contains(

                    text,

                    "return",

                    "replace",

                    "replacement",

                    "exchange"

            )

    ){


        return "I can help with replacement. Please share your order number and reason for replacement.";


    }










    // REFUND


    if(

            contains(

                    text,

                    "refund",

                    "cashback",

                    "money back"

            )

    ){


        return "I will help check your refund. Please share your order ID and refund request date.";


    }










    // LOGIN


    if(

            contains(

                    text,

                    "login",

                    "password",

                    "otp",

                    "account"

            )

    ){


        return "It seems like an account access issue. Try password reset first. If it fails, tell me the exact error message shown.";


    }









    // CANCEL ORDER


    if(

            contains(

                    text,

                    "cancel",

                    "cancellation"

            )

    ){


        return "I can help cancel your order. Please share the order number and I will check cancellation availability.";


    }









    // WARRANTY


    if(

            contains(

                    text,

                    "warranty",

                    "guarantee"

            )

    ){


        return "I can check warranty details. Please share your order number and product issue.";


    }









    // ADDRESS CHANGE


    if(

            contains(

                    text,

                    "address",

                    "wrong address",

                    "change address"

            )

    ){


        return "I can help update delivery details. Please share your order number and correct address information.";


    }










    // COMPLAINT


    if(

            contains(

                    text,

                    "bad",

                    "angry",

                    "worst",

                    "stupid",

                    "fucker",

                    "hate"

            )

    ){


        return "I understand your frustration. I will help resolve this. Please explain what went wrong.";


    }










    // THANKS


    if(

            contains(

                    lastMessage,

                    "thanks",

                    "thank you",

                    "done"

            )

    ){


        return "You're welcome 😊 Happy to help. Your support request has been updated.";


    }









    return "I understand your concern. Please share your order number or a few more details so I can assist you properly.";


}











   private String extractLastUserMessage(

        String text

){



    if(text == null){


        return "";


    }




    String[] messages =

            text.split(

                    "user:"

            );





    if(messages.length > 0){


        return messages[

                messages.length - 1

        ];


    }





    return text;


}











    private boolean contains(

            String text,

            String... words

    ){



        for(String word : words){



            if(

                    text.contains(

                            word

                    )

            ){


                return true;


            }


        }





        return false;


    }




}