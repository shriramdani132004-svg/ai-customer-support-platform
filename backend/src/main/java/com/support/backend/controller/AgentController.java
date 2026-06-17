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

import com.support.backend.service.AgentService;








@RestController

@RequestMapping("/api/agent")

public class AgentController {





    private final AgentService agentService;







    public AgentController(

            AgentService agentService

    ){


        this.agentService =
                agentService;


    }










    @GetMapping("/escalated")

    public List<Ticket> escalatedTickets(){



        return agentService.getEscalatedTickets();



    }










    @PostMapping("/tickets/{id}/reply")

    public ChatMessage reply(

            @PathVariable Long id,

            @RequestBody String message

    ){



        return agentService.reply(

                id,

                message

        );



    }











    @PutMapping("/tickets/{id}/resolve")

    public Ticket resolve(

            @PathVariable Long id

    ){



        return agentService.resolveTicket(

                id

        );



    }




}