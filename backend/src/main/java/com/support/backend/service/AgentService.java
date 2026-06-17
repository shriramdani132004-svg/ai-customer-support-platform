package com.support.backend.service;



import java.time.LocalDateTime;

import java.util.List;


import org.springframework.stereotype.Service;


import com.support.backend.entity.ChatMessage;

import com.support.backend.entity.Ticket;


import com.support.backend.exception.ResourceNotFoundException;


import com.support.backend.repository.ChatMessageRepository;

import com.support.backend.repository.TicketRepository;






@Service
public class AgentService {




    private final TicketRepository ticketRepository;


    private final ChatMessageRepository chatMessageRepository;








    public AgentService(

            TicketRepository ticketRepository,

            ChatMessageRepository chatMessageRepository

    ){



        this.ticketRepository =
                ticketRepository;



        this.chatMessageRepository =
                chatMessageRepository;



    }










    public List<Ticket> getEscalatedTickets(){



        return ticketRepository.findByStatus(

                "HUMAN_REQUIRED"

        );



    }










    public ChatMessage reply(

            Long id,

            String messageText

    ){





        Ticket ticket =

                ticketRepository

                .findById(id)

                .orElseThrow(

                        () -> new ResourceNotFoundException(

                                "Ticket not found"

                        )

                );








        ChatMessage message =
                new ChatMessage();






        message.setSender(

                "AGENT"

        );





        message.setMessage(

                messageText

        );






        message.setCreatedAt(

                LocalDateTime.now()

        );






        message.setTicket(

                ticket

        );







        return chatMessageRepository.save(

                message

        );



    }











    public Ticket resolveTicket(

            Long id

    ){





        Ticket ticket =

                ticketRepository

                        .findById(id)

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );








        ticket.setStatus(

                "RESOLVED"

        );





        ticket.setEscalated(

                false

        );







        return ticketRepository.save(

                ticket

        );



    }





}