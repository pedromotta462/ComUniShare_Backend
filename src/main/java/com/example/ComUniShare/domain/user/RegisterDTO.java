package com.example.ComUniShare.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String login,
        @NotBlank
        String password,
        @NotBlank
        String fone,
        @NotBlank
        String address,

        UserRole role
) {
}
