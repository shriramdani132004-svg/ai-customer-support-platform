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
public class TicketService {





    private final TicketRepository ticketRepository;


    private final AiService aiService;


    private final EmailService emailService;


    private final ChatMessageRepository chatMessageRepository;







    public TicketService(

            TicketRepository ticketRepository,

            AiService aiService,

            EmailService emailService,

            ChatMessageRepository chatMessageRepository

    ){



        this.ticketRepository =
                ticketRepository;



        this.aiService =
                aiService;



        this.emailService =
                emailService;



        this.chatMessageRepository =
                chatMessageRepository;



    }










    public Ticket createTicket(

            Ticket ticket

    ){





        if(ticket.getStatus()==null){


            ticket.setStatus(

                    "OPEN"

            );


        }






        if(ticket.getAttemptCount()==null){


            ticket.setAttemptCount(

                    0

            );


        }







        if(ticket.getEscalated()==null){


            ticket.setEscalated(

                    false

            );


        }







        return ticketRepository.save(

                ticket

        );



    }












    public List<Ticket> getAllTickets(){



        return ticketRepository.findAll();



    }












    public Ticket increaseAttempt(

            Long ticketId

    ){





        Ticket ticket =

                ticketRepository

                        .findById(

                                ticketId

                        )

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );









        int currentAttempts = 0;





        if(ticket.getAttemptCount()!=null){


            currentAttempts =

                    ticket.getAttemptCount();


        }








        int newAttempts =

                currentAttempts + 1;







        ticket.setAttemptCount(

                newAttempts

        );










        if(

                newAttempts >= 5

                &&

                !"RESOLVED".equals(

                        ticket.getStatus()

                )

                &&

                !Boolean.TRUE.equals(

                        ticket.getEscalated()

                )

        ){







            ticket.setEscalated(

                    true

            );






            ticket.setStatus(

                    "HUMAN_REQUIRED"

            );







            emailService.sendEscalationEmail(

                    ticket

            );



        }









        return ticketRepository.save(

                ticket

        );



    }













    public ChatMessage generateAiResponse(

            Long ticketId

    ){






        Ticket ticket =

                ticketRepository

                        .findById(

                                ticketId

                        )

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );








        increaseAttempt(

                ticketId

        );









        String aiReply =

                aiService.generateReply(

                        ticket.getDescription()

                );










        ChatMessage message =

                new ChatMessage();







        message.setSender(

                "AI"

        );







        message.setMessage(

                aiReply

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


    public List<ChatMessage> getTicketMessages(

            Long ticketId

    ){



        ticketRepository

                .findById(ticketId)

                .orElseThrow(

                        () -> new ResourceNotFoundException(

                                "Ticket not found"

                        )

                );






        return chatMessageRepository.findByTicketId(

                ticketId

        );



    }



}