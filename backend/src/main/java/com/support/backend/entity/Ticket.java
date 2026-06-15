package com.support.backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name="tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;


    private String description;


    private String status;


    private String priority;


    private LocalDateTime createdAt;


    @PrePersist
    public void createTime(){

        createdAt = LocalDateTime.now();

    }

}