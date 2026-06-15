package com.support.backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name="chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String sender;


    @Column(length = 5000)
    private String message;


    private LocalDateTime createdAt;


    @PrePersist
    public void createTime(){

        createdAt = LocalDateTime.now();

    }

}