package com.example.ComUniShare.domain.chat;

import jakarta.validation.constraints.NotBlank;

public record ChatRequestDTO(
        @NotBlank
        String user2Id
) {}
