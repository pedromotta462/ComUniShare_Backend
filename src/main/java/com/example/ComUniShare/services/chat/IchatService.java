package com.example.ComUniShare.services.chat;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.chat.ChatRequestDTO;
import com.example.ComUniShare.domain.chat.ChatResponseDTO;
import com.example.ComUniShare.domain.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IchatService {

    List<ChatResponseDTO> getAllChatsForUser();

    ResponseEntity<String> getOrCreateChat(ChatRequestDTO user2Id);

    Chat getChatById(String chatId);

    ResponseEntity<String> deleteChatById(String chatId);
}
