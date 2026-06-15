package com.support.backend.service;


import com.support.backend.entity.ChatMessage;
import com.support.backend.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatMessageService {


    private final ChatMessageRepository chatMessageRepository;



    public ChatMessage createMessage(ChatMessage message) {

        return chatMessageRepository.save(message);

    }



    public List<ChatMessage> getAllMessages() {

        return chatMessageRepository.findAll();

    }


}