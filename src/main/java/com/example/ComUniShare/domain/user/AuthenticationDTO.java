package com.example.ComUniShare.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @Email
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
