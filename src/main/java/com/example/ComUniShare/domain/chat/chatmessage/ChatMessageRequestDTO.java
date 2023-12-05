package com.example.ComUniShare.domain.chat.chatmessage;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequestDTO(
        @NotBlank
        String chatId,
        @NotBlank
        String message
) {
}
