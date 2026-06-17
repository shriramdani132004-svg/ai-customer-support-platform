package com.support.backend.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.support.backend.entity.ChatMessage;
import com.support.backend.entity.Ticket;
import com.support.backend.exception.ResourceNotFoundException;
import com.support.backend.repository.ChatMessageRepository;
import com.support.backend.repository.TicketRepository;
import org.springframework.data.redis.core.RedisTemplate;
import com.support.backend.kafka.TicketEventProducer;


@Service
public class TicketService {


    private final TicketRepository ticketRepository;

    private final AiService aiService;

    private final EmailService emailService;

    private final TicketAnalysisService ticketAnalysisService;

    private final ChatMessageRepository chatMessageRepository;

    private final PriorityService priorityService;

    private final SummaryService summaryService;

	private final RedisTemplate<String,Object> redisTemplate;

	private final TicketEventProducer ticketEventProducer;



    public TicketService(

            TicketRepository ticketRepository,

            AiService aiService,

            EmailService emailService,

            TicketAnalysisService ticketAnalysisService,

            ChatMessageRepository chatMessageRepository,

            PriorityService priorityService,

            SummaryService summaryService,

		RedisTemplate<String,Object> redisTemplate,

		TicketEventProducer ticketEventProducer

    ){

        this.ticketRepository = ticketRepository;

        this.aiService = aiService;

        this.emailService = emailService;

        this.ticketAnalysisService = ticketAnalysisService;

        this.chatMessageRepository = chatMessageRepository;

        this.priorityService = priorityService;

        this.summaryService = summaryService;

	this.redisTemplate = redisTemplate;

	this.ticketEventProducer = ticketEventProducer;

    }







    public Ticket createTicket(Ticket ticket){


        if(ticket.getStatus()==null){

            ticket.setStatus("OPEN");

        }


        if(ticket.getAttemptCount()==null){

            ticket.setAttemptCount(0);

        }


        if(ticket.getEscalated()==null){

            ticket.setEscalated(false);

        }




        String issueText =

                ticket.getTitle()

                +

                " "

                +

                ticket.getDescription();




        ticket.setPriority(

                ticketAnalysisService.detectPriority(issueText)

        );



        ticket.setCategory(

                ticketAnalysisService.detectCategory(issueText)

        );



        ticket.setSummary(

                ticketAnalysisService.generateSummary(issueText)

        );



        ticket.setPriority(

                priorityService.detectPriority(

                        ticket.getDescription()

                )

        );



        Ticket saved = ticketRepository.save(ticket);


redisTemplate.delete(

        "tickets"

);


return saved;

    }








    public List<Ticket> getAllTickets(){



    Object cachedTickets =

            redisTemplate

                    .opsForValue()

                    .get("tickets");




    if(cachedTickets != null){


        System.out.println(

                "Tickets loaded from Redis"

        );


        return (List<Ticket>) cachedTickets;


    }






    System.out.println(

            "Tickets loaded from Database"

    );




    List<Ticket> tickets =

            ticketRepository.findAll();





    redisTemplate

            .opsForValue()

            .set(

                    "tickets",

                    tickets

            );




    return tickets;


}









    public Ticket increaseAttempt(Long ticketId){



        Ticket ticket =

                ticketRepository

                        .findById(ticketId)

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );




        int attempts = 0;



        if(ticket.getAttemptCount()!=null){

            attempts = ticket.getAttemptCount();

        }



        attempts++;



        ticket.setAttemptCount(attempts);





        if(

                attempts >= 5

                &&

                !Boolean.TRUE.equals(ticket.getEscalated())

        ){



            ticket.setEscalated(true);



            ticket.setStatus(

                    "HUMAN_REQUIRED"

            );




            ticket.setConversationSummary(

                    summaryService.generateSummary(

                            ticket.getDescription()

                    )

            );




            ticketEventProducer.sendEscalationEvent(

        	ticket.getId()

		);


        }




        return ticketRepository.save(ticket);

    }











    public ChatMessage generateAiResponse(

            Long ticketId,

            String userMessage

    ){



        Ticket ticket =

                ticketRepository

                        .findById(ticketId)

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );


		ChatMessage userChat = new ChatMessage();


userChat.setSender(

        "USER"

);


userChat.setMessage(

        userMessage

);


userChat.setCreatedAt(

        LocalDateTime.now()

);


userChat.setTicket(

        ticket

);


chatMessageRepository.save(

        userChat

);

		if(

        "RESOLVED".equals(

                ticket.getStatus()

        )

){


        ChatMessage message =

                new ChatMessage();



        message.setSender(

                "SYSTEM"

        );



        message.setMessage(

                "This ticket has already been resolved and closed. Please create a new ticket if you need more help."

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




        if("RESOLVED".equals(ticket.getStatus())){


    ChatMessage message = new ChatMessage();


    message.setSender("SYSTEM");


    message.setMessage(

            "This ticket is already resolved and closed. Please create a new ticket if you need further support."

    );


    message.setCreatedAt(

            LocalDateTime.now()

    );


    message.setTicket(ticket);


    return chatMessageRepository.save(message);


}




if(Boolean.TRUE.equals(ticket.getEscalated())){


    ChatMessage message = new ChatMessage();


    message.setSender("SYSTEM");


    message.setMessage(

            "Your ticket is assigned to a human support agent. Please wait for the agent response."

    );


    message.setCreatedAt(

            LocalDateTime.now()

    );


    message.setTicket(ticket);


    return chatMessageRepository.save(message);


}








        Ticket updatedTicket =

                increaseAttempt(ticketId);






        if(Boolean.TRUE.equals(updatedTicket.getEscalated())){



            ChatMessage message = new ChatMessage();



            message.setSender("SYSTEM");



            message.setMessage(

                    "AI could not resolve your issue after multiple attempts. Your ticket has been escalated to our human support team and email notification has been sent."

            );



            message.setCreatedAt(

                    LocalDateTime.now()

            );



            message.setTicket(updatedTicket);



            return chatMessageRepository.save(message);

        }








        List<ChatMessage> oldMessages =

                chatMessageRepository.findByTicketId(ticketId);




        StringBuilder conversation =

                new StringBuilder();




        for(ChatMessage old : oldMessages){


            conversation

                    .append(old.getSender())

                    .append(": ")

                    .append(old.getMessage())

                    .append("\n");

        }






        conversation

                .append("USER: ")

                .append(userMessage);







        String aiReply =

                aiService.generateReply(

                        conversation.toString()

                );






        ChatMessage message = new ChatMessage();



        message.setSender("AI");



        message.setMessage(aiReply);



        message.setCreatedAt(

                LocalDateTime.now()

        );



        message.setTicket(ticket);





        return chatMessageRepository.save(message);


    }










    public List<ChatMessage> getTicketMessages(Long ticketId){



        ticketRepository

                .findById(ticketId)

                .orElseThrow(

                        () -> new ResourceNotFoundException(

                                "Ticket not found"

                        )

                );




        return chatMessageRepository.findByTicketId(ticketId);

    }


}