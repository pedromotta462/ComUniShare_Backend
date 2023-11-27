package com.example.ComUniShare.domain.user;

import com.example.ComUniShare.domain.product.Product;

import java.util.List;

public record UserResponseDTO(String id, String name, String login, String fone, String address, UserRole role) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getFone(),
                user.getAddress(),
                user.getRole()
        );
    }
}
