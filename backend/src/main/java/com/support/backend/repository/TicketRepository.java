package com.support.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.support.backend.entity.Ticket;


public interface TicketRepository
extends JpaRepository<Ticket,Long> {


}