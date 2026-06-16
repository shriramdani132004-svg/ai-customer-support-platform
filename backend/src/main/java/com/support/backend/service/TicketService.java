package com.support.backend.service;


import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;


import com.support.backend.entity.ChatMessage;
import com.support.backend.entity.Ticket;


import com.support.backend.repository.ChatMessageRepository;
import com.support.backend.repository.TicketRepository;



@Service
public class TicketService {



    private final TicketRepository ticketRepository;


    private final AiService aiService;


    private final ChatMessageRepository chatMessageRepository;





    public TicketService(

            TicketRepository ticketRepository,

            AiService aiService,

            ChatMessageRepository chatMessageRepository

    ) {


        this.ticketRepository =
                ticketRepository;


        this.aiService =
                aiService;


        this.chatMessageRepository =
                chatMessageRepository;


    }






    public Ticket createTicket(
            Ticket ticket
    ) {


        return ticketRepository.save(
                ticket
        );


    }







    public List<Ticket> getAllTickets() {


        return ticketRepository.findAll();


    }








    public ChatMessage generateAiResponse(
            Long ticketId
    ) {




        Ticket ticket =

                ticketRepository

                .findById(ticketId)

                .orElseThrow();






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



}