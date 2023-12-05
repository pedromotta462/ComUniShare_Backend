package com.example.ComUniShare.domain.chat.chatmessage;

import java.util.Date;

public record ChatMessageResponseDTO(
        String messageId,
        String senderId,
        String senderName,
        String message,
        Date sentAt
) {
    public ChatMessageResponseDTO(ChatMessage chatMessage){
        this(
                chatMessage.getId(),
                chatMessage.getSender() != null ? chatMessage.getSender().getId() : null,
                chatMessage.getSender() != null ? chatMessage.getSender().getName() : null,
                chatMessage.getMessage(),
                chatMessage.getSentAt()
        );
    }
}
