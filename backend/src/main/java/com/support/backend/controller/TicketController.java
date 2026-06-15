package com.support.backend.controller;


import com.support.backend.entity.Ticket;
import com.support.backend.service.TicketService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {


    private final TicketService ticketService;



    @PostMapping
    public Ticket createTicket(
            @RequestBody Ticket ticket
    ) {

        return ticketService.createTicket(ticket);

    }



    @GetMapping
    public List<Ticket> getTickets() {

        return ticketService.getAllTickets();

    }



    @GetMapping("/{id}")
    public Ticket getTicket(
            @PathVariable Long id
    ) {

        return ticketService.getTicketById(id);

    }


}