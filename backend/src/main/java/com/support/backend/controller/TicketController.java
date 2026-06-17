package com.support.backend.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.support.backend.entity.ChatMessage;
import com.support.backend.entity.Ticket;
import com.support.backend.service.TicketService;






@RestController

@RequestMapping("/api/tickets")

public class TicketController {





    private final TicketService ticketService;







    public TicketController(

            TicketService ticketService

    ){



        this.ticketService = ticketService;



    }










    @PostMapping

    public Ticket create(

            @RequestBody Ticket ticket

    ){



        return ticketService.createTicket(

                ticket

        );



    }










    @GetMapping

    public List<Ticket> getAll(){



        return ticketService.getAllTickets();



    }











    @PostMapping("/{id}/ai-response")

    public ChatMessage generateAiResponse(

            @PathVariable Long id

    ){



        return ticketService.generateAiResponse(

                id

        );



    }












    @PutMapping("/{id}/attempt")

    public Ticket increaseAttempt(

            @PathVariable Long id

    ){



        return ticketService.increaseAttempt(

                id

        );



    }





    @GetMapping("/{id}/messages")

    public List<ChatMessage> getMessages(

            @PathVariable Long id

    ){



        return ticketService.getTicketMessages(

                id

        );



    }




}