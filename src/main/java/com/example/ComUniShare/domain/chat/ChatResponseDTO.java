package com.example.ComUniShare.domain.chat;

public record ChatResponseDTO(
        String chatId,
        String userId1,
        String userName1,
        String userId2,
        String userName2
) {
    public ChatResponseDTO(Chat chat){
        this(
                chat.getId(),
                chat.getUser1() != null ? chat.getUser1().getId() : null,
                chat.getUser1() != null ? chat.getUser1().getName() : null,
                chat.getUser2() != null ? chat.getUser2().getId() : null,
                chat.getUser2() != null ? chat.getUser2().getName() : null
        );
    }
}
