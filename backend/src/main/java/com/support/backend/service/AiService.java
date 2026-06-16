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





    public AiService(
            WebClient webClient
    ) {


        this.webClient = webClient;


    }






    public String generateReply(
            String customerProblem
    ) {




        Map<String,Object> requestBody =
                Map.of(


                        "model",

                        model,



                        "messages",

                        List.of(


                                Map.of(

                                        "role",

                                        "system",


                                        "content",

                                        "You are a professional customer support AI assistant."

                                ),



                                Map.of(

                                        "role",

                                        "user",


                                        "content",

                                        customerProblem

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


                        .bodyValue(requestBody)



                        .retrieve()


                        .bodyToMono(Map.class)


                        .block();







        List choices =
                (List) response.get(
                        "choices"
                );




        Map choice =
                (Map) choices.get(0);




        Map message =
                (Map) choice.get(
                        "message"
                );




        return message

                .get("content")

                .toString();


    }



}