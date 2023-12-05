package com.example.ComUniShare.repositories;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.chat.chatmessage.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findAllByChat(Chat chat);
}
