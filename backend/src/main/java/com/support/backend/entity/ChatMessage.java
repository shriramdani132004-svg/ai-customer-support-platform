package com.support.backend.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;



@Entity
@Data
public class ChatMessage {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String sender;



    private String message;



    private LocalDateTime createdAt;



    @ManyToOne
    private Ticket ticket;



}