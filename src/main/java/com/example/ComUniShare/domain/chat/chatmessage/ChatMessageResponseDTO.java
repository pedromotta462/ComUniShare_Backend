package com.example.ComUniShare.domain.chat.chatmessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public record ChatMessageResponseDTO(
        String messageId,
        String senderId,
        String senderName,
        String message,
        String sentAt
) {
    public ChatMessageResponseDTO(ChatMessage chatMessage){
        this(
                chatMessage.getId(),
                chatMessage.getSender() != null ? chatMessage.getSender().getId() : null,
                chatMessage.getSender() != null ? chatMessage.getSender().getName() : null,
                chatMessage.getMessage(),
                formatDateTime(chatMessage.getSentAt())
        );
    }

    private static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(date);
    }
}

