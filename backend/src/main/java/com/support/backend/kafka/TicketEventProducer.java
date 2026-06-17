package com.support.backend.kafka;


import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;



@Service
public class TicketEventProducer {


    private final KafkaTemplate<String,String> kafkaTemplate;



    public TicketEventProducer(

            KafkaTemplate<String,String> kafkaTemplate

    ){

        this.kafkaTemplate = kafkaTemplate;

    }



    public void sendEscalationEvent(

            Long ticketId

    ){


        kafkaTemplate.send(

                "ticket-escalated",

                ticketId.toString()

        );


        System.out.println(

                "Kafka event sent for ticket: " + ticketId

        );


    }


}