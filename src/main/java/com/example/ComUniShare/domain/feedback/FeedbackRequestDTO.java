package com.example.ComUniShare.domain.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FeedbackRequestDTO (
    @NotBlank
    String comment,

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    @NotNull
    int rating,

    @NotBlank
    String productId

){}
