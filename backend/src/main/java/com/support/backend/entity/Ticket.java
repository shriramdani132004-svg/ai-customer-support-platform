package com.support.backend.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;



@Entity

@Table(name = "tickets")

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





    private String category;





    private String summary;





    private String conversationSummary;





    private Integer attemptCount = 0;





    private Boolean escalated = false;





    private LocalDateTime createdAt;






    @ManyToOne

    @JoinColumn(name = "user_id")

    private User user;


	@ManyToOne

@JoinColumn(name = "order_id")

private Order order;




    @PrePersist

    public void createTime(){


        createdAt = LocalDateTime.now();


        if(status == null){


            status = "OPEN";


        }


        if(attemptCount == null){


            attemptCount = 0;


        }


        if(escalated == null){


            escalated = false;


        }


    }



}