package com.example.ComUniShare.domain.product;

public record ProductResponseDTO(String id, String name, String description, String type, Integer price, String ownerId, String ownerName) {
    public ProductResponseDTO(Product product){
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                String.valueOf(product.getType()),
                product.getPrice(),
                product.getUser() != null ? product.getUser().getId() : null,
                product.getUser() != null ? product.getUser().getName() : null
        );
    }
}

