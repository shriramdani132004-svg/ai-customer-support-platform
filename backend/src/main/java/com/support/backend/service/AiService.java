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

        this.webClient = WebClient.builder().build();

    }





    public String generateReply(String conversation){


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

                                            "You are a professional customer support agent. Continue the conversation naturally. Remember previous messages. Give short helpful replies."

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

                    webClient.post()

                            .uri(apiUrl)

                            .header(

                                    "Authorization",

                                    "Bearer " + apiKey

                            )


                            .header(

                                    "Content-Type",

                                    "application/json"

                            )


                            .bodyValue(request)


                            .retrieve()


                            .bodyToMono(Map.class)


                            .block();





            List choices =

                    (List) response.get("choices");


            Map choice =

                    (Map) choices.get(0);


            Map message =

                    (Map) choice.get("message");


            return message.get("content").toString();


        }


        catch(Exception e){



           String text = conversation
        .substring(
                conversation.lastIndexOf("USER:")
        )
        .toLowerCase();




            if(text.endsWith("user: hi")
                    ||
               text.endsWith("user: hello")
                    ||
               text.endsWith("user: hii")
                    ||
               text.endsWith("user: hey")){


                return "Hello 👋 I am your AI support assistant. Please tell me your issue, I will help you solve it.";

            }






            if(text.contains("how are you")){


                return "I am doing well 😊 Thanks for asking. I am ready to help you with your support request.";

            }






            if(text.contains("payment")
                    &&
               text.contains("order")){


                return "I understand. Your payment was completed but the order was not created. Please wait a few minutes and check your order history. If it is still missing, share your payment reference so billing support can trace it.";

            }






            if(text.contains("payment")
                    ||
               text.contains("refund")
                    ||
               text.contains("money")
                    ||
               text.contains("amount")){


                if(text.contains("dont have")
                        ||
                   text.contains("don't have")){


                    return "No problem. Please check your bank statement, UPI app, or payment history. You can share the transaction date and amount instead.";

                }



                return "I see a payment related issue. Can you share your transaction ID, payment time, or amount deducted? I will help verify the next step.";

            }








            if(text.contains("login")
                    ||
               text.contains("password")){


                return "I see a login issue. Try resetting your password first. If you still cannot access your account, I can help escalate it.";

            }








            if(text.contains("not done")
                    ||
               text.contains("not working")
                    ||
               text.contains("still")){


                return "I understand the issue is still not fixed. Let us continue troubleshooting. Tell me what happened after trying the previous step.";

            }








            if(text.contains("fucker")){


                return "I understand you are frustrated. I am here to help. Please explain the issue and we will work through it.";

            }








            return "I understand. Please share a little more information so I can help you with the correct solution.";



        }


    }


}