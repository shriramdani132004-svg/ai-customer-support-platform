package com.support.backend.service;


import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.support.backend.entity.ChatMessage;
import com.support.backend.entity.Ticket;
import com.support.backend.entity.User;


import com.support.backend.exception.ResourceNotFoundException;


import com.support.backend.kafka.TicketEventProducer;


import com.support.backend.repository.ChatMessageRepository;
import com.support.backend.repository.TicketRepository;
import com.support.backend.repository.UserRepository;





@Service
public class TicketService {


    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final AiService aiService;

    private final TicketAnalysisService ticketAnalysisService;

    private final ChatMessageRepository chatMessageRepository;

    private final PriorityService priorityService;

    private final SummaryService summaryService;

    private final RedisTemplate<String,Object> redisTemplate;

    private final TicketEventProducer ticketEventProducer;





    public TicketService(

            TicketRepository ticketRepository,

            UserRepository userRepository,

            AiService aiService,

            TicketAnalysisService ticketAnalysisService,

            ChatMessageRepository chatMessageRepository,

            PriorityService priorityService,

            SummaryService summaryService,

            RedisTemplate<String,Object> redisTemplate,

            TicketEventProducer ticketEventProducer

    ){


        this.ticketRepository = ticketRepository;

        this.userRepository = userRepository;

        this.aiService = aiService;

        this.ticketAnalysisService = ticketAnalysisService;

        this.chatMessageRepository = chatMessageRepository;

        this.priorityService = priorityService;

        this.summaryService = summaryService;

        this.redisTemplate = redisTemplate;

        this.ticketEventProducer = ticketEventProducer;


    }










    private User getCurrentUser(){


        String email =

                SecurityContextHolder

                        .getContext()

                        .getAuthentication()

                        .getName();




        return userRepository

                .findByEmail(email)

                .orElseThrow(

                        () -> new ResourceNotFoundException(

                                "User not found"

                        )

                );


    }










    public Ticket createTicket(

            Ticket ticket

    ){


        User user = getCurrentUser();


        ticket.setUser(

                user

        );






        String issueText =

                ticket.getTitle()

                +

                " "

                +

                ticket.getDescription();





        ticket.setPriority(

                priorityService.detectPriority(

                        issueText

                )

        );




        ticket.setCategory(

                ticketAnalysisService.detectCategory(

                        issueText

                )

        );





        ticket.setSummary(

                ticketAnalysisService.generateSummary(

                        issueText

                )

        );







        Ticket saved =

                ticketRepository.save(

                        ticket

                );





        redisTemplate.delete(

                "tickets"

        );





        return saved;


    }











    public List<Ticket> getAllTickets(){



        User user = getCurrentUser();



        return ticketRepository.findByUser(

                user

        );


    }












    public Ticket increaseAttempt(

            Long ticketId

    ){


        Ticket ticket =

                ticketRepository.findById(ticketId)

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );




        int attempts =

                ticket.getAttemptCount()==null

                        ? 0

                        : ticket.getAttemptCount();




        attempts++;


        ticket.setAttemptCount(

                attempts

        );







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





        return ticketRepository.save(

                ticket

        );


    }









    public ChatMessage generateAiResponse(

            Long ticketId,

            String userMessage

    ){



        Ticket ticket =

                ticketRepository.findById(ticketId)

                        .orElseThrow(

                                () -> new ResourceNotFoundException(

                                        "Ticket not found"

                                )

                        );





        ChatMessage userChat = new ChatMessage();


        userChat.setSender("USER");

        userChat.setMessage(userMessage);

        userChat.setCreatedAt(LocalDateTime.now());

        userChat.setTicket(ticket);


        chatMessageRepository.save(userChat);






        if("RESOLVED".equals(ticket.getStatus())){


            ChatMessage msg = new ChatMessage();


            msg.setSender("SYSTEM");


            msg.setMessage(

                    "Ticket already resolved. Create a new ticket for more help."

            );


            msg.setCreatedAt(

                    LocalDateTime.now()

            );


            msg.setTicket(ticket);


            return chatMessageRepository.save(msg);


        }







        if(Boolean.TRUE.equals(ticket.getEscalated())){


            ChatMessage msg = new ChatMessage();


            msg.setSender("SYSTEM");


            msg.setMessage(

                    "Your ticket is assigned to a human support agent."

            );


            msg.setCreatedAt(LocalDateTime.now());


            msg.setTicket(ticket);


            return chatMessageRepository.save(msg);


        }








        Ticket updatedTicket =

                increaseAttempt(ticketId);





        if(Boolean.TRUE.equals(updatedTicket.getEscalated())){


            ChatMessage msg = new ChatMessage();


            msg.setSender("SYSTEM");


            msg.setMessage(

                    "AI could not resolve your issue. Ticket escalated to human support."

            );


            msg.setCreatedAt(LocalDateTime.now());


            msg.setTicket(updatedTicket);


            return chatMessageRepository.save(msg);


        }








        List<ChatMessage> oldMessages =

        chatMessageRepository.findByTicketId(

                ticketId

        );




StringBuilder conversation =

        new StringBuilder();





conversation.append(

        "You are a professional customer support AI assistant.\n"

);


conversation.append(

        "Rules:\n"

);


conversation.append(

        "- Understand the customer's issue.\n"

);


conversation.append(

        "- Do not repeat the same answer again and again.\n"

);


conversation.append(

        "- Remember previous conversation messages.\n"

);


conversation.append(

        "- Ask for missing details only when needed.\n"

);


conversation.append(

        "- Give practical solution steps.\n"

);


conversation.append(

        "- If user is angry, stay calm and helpful.\n"

);


conversation.append(

        "- Keep replies short like a real support chat.\n\n"

);






conversation.append(

        "Ticket Details:\n"

);



conversation.append(

        "Title: "

);

conversation.append(

        ticket.getTitle()

);



conversation.append(

        "\nDescription: "

);


conversation.append(

        ticket.getDescription()

);



conversation.append(

        "\nCategory: "

);


conversation.append(

        ticket.getCategory()

);



conversation.append(

        "\nPriority: "

);


conversation.append(

        ticket.getPriority()

);



conversation.append(

        "\n\nConversation History:\n"

);







for(ChatMessage old : oldMessages){



    conversation

            .append(old.getSender())

            .append(": ")

            .append(old.getMessage())

            .append("\n");



}







String aiReply =

        aiService.generateReply(

                conversation.toString()

        );






        ChatMessage ai = new ChatMessage();


        ai.setSender("AI");


        ai.setMessage(

                aiReply

        );


        ai.setCreatedAt(

                LocalDateTime.now()

        );


        ai.setTicket(ticket);




        return chatMessageRepository.save(ai);


    }









    public List<ChatMessage> getTicketMessages(

            Long ticketId

    ){


        return chatMessageRepository.findByTicketId(

                ticketId

        );


    }

public void deleteTicket(

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





    chatMessageRepository.deleteAll(

            chatMessageRepository.findByTicketId(

                    id

            )

    );





    ticketRepository.delete(

            ticket

    );





    redisTemplate.delete(

            "tickets"

    );


}

}