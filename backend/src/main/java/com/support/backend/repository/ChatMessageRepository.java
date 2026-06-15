package com.support.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.support.backend.entity.ChatMessage;


public interface ChatMessageRepository
extends JpaRepository<ChatMessage,Long>{


}