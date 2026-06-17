package com.support.backend.controller;


import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



import com.support.backend.entity.Ticket;

import com.support.backend.service.CustomerTicketService;






@RestController

@RequestMapping("/api/customer/tickets")

public class CustomerTicketController {




    private final CustomerTicketService customerTicketService;





    public CustomerTicketController(

            CustomerTicketService customerTicketService

    ){


        this.customerTicketService = customerTicketService;


    }








    @GetMapping

    public List<Ticket> myTickets(){



        return customerTicketService.myTickets();



    }










    @PostMapping("/order/{orderId}")

    public Ticket createTicket(

            @PathVariable Long orderId,

            @RequestBody Ticket ticket

    ){



        return customerTicketService.createTicket(

                orderId,

                ticket

        );



    }



}