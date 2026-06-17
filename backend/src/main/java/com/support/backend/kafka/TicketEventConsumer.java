package com.support.backend.kafka;


import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;


import com.support.backend.entity.Ticket;

import com.support.backend.repository.TicketRepository;

import com.support.backend.service.EmailService;



@Service
public class TicketEventConsumer {


    private final EmailService emailService;


    private final TicketRepository ticketRepository;




    public TicketEventConsumer(

            EmailService emailService,

            TicketRepository ticketRepository

    ){

        this.emailService = emailService;

        this.ticketRepository = ticketRepository;

    }







    @KafkaListener(

            topics = "ticket-escalated",

            groupId = "support-group"

    )

    public void handleEscalation(

            String ticketId

    ){


        System.out.println(

                "Kafka event received: " + ticketId

        );




        Ticket ticket =

                ticketRepository

                        .findById(

                                Long.valueOf(ticketId)

                        )

                        .orElseThrow();




        emailService.sendEscalationEmail(

                ticket

        );


    }


}