package com.support.backend.service;


import com.support.backend.entity.Ticket;
import com.support.backend.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {


    private final TicketRepository ticketRepository;



    public Ticket createTicket(Ticket ticket) {

        return ticketRepository.save(ticket);

    }



    public List<Ticket> getAllTickets() {

        return ticketRepository.findAll();

    }



    public Ticket getTicketById(Long id) {

        return ticketRepository.findById(id)
                .orElseThrow(
                    () -> new RuntimeException("Ticket not found")
                );

    }


}