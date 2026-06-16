package com.support.backend.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;


@Service
public class AiService {


    private final WebClient webClient;


    @Value("${ai.api.key}")
    private String apiKey;


    @Value("${ai.api.url}")
    private String apiUrl;


    @Value("${ai.model}")
    private String model;


    public AiService() {

        this.webClient = WebClient.builder().build();

    }



    public String generateReply(String ticketText) {


        try {


            Map<String, Object> request =
                    Map.of(

                            "model",
                            model,


                            "messages",
                            List.of(

                                    Map.of(
                                            "role",
                                            "system",

                                            "content",
                                            "You are a helpful customer support assistant."
                                    ),


                                    Map.of(
                                            "role",
                                            "user",

                                            "content",
                                            ticketText
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

        catch (Exception e) {


            return "AI service is currently unavailable. Your ticket has been received and our support team will review it.";


        }


    }


}