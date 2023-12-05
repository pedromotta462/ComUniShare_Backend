package com.example.ComUniShare.services.chat.chatmessage;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.chat.chatmessage.ChatMessage;
import com.example.ComUniShare.domain.chat.chatmessage.ChatMessageResponseDTO;
import com.example.ComUniShare.domain.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IchatMessageService {

    ResponseEntity<String> sendChatMessage(String chatId, String message);

    List<ChatMessageResponseDTO> getChatMessagesForChat(String chatId);
}
