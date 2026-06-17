package com.support.backend.entity;


import java.time.LocalDateTime;


import jakarta.persistence.*;


import lombok.*;




@Entity

@Table(name = "orders")

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class Order {




    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;





    private String orderNumber;





    private String productName;





    private Double price;





    private String status;





    private LocalDateTime createdAt;






    @ManyToOne

    @JoinColumn(name = "user_id")

    private User user;






    @PrePersist

    public void createTime(){


        createdAt = LocalDateTime.now();


    }



}