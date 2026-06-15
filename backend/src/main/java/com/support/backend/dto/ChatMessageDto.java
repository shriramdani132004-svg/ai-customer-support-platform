package com.support.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {


    private Long id;


    private String message;


    private String sender;


    private Long ticketId;


}