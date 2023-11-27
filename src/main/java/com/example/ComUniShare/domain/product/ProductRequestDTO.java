package com.example.ComUniShare.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigInteger;

public record ProductRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        @Pattern(regexp = "^(?i)(ITEM|SERVICO)$", message = "The type should be ITEM or SERVICO")
        String type,

        @NotNull
        Integer price
) {
}
