package com.example.ComUniShare.domain.feedback;

public record FeedbackResponseDTO (
        String id,

        int rating,
        String comment,

        String productId,

        String productName,

        String ownerId,

        String ownerName

){
    public FeedbackResponseDTO(Feedback feedback){
        this(
                feedback.getId(),
                feedback.getRating(),
                feedback.getComment(),
                feedback.getProduct() != null ? feedback.getProduct().getId() : null,
                feedback.getProduct() != null ? feedback.getProduct().getName() : null,
                feedback.getUser() != null ? feedback.getUser().getId() : null,
                feedback.getUser() != null ? feedback.getUser().getName() : null
        );
    }
}
