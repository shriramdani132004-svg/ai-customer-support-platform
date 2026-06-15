package com.support.backend.controller;


import com.support.backend.entity.ChatMessage;
import com.support.backend.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class ChatMessageController {


    private final ChatMessageService chatMessageService;


    @PostMapping
    public ChatMessage createMessage(@RequestBody ChatMessage message) {

        return chatMessageService.createMessage(message);

    }


    @GetMapping
    public List<ChatMessage> getMessages() {

        return chatMessageService.getAllMessages();

    }


}