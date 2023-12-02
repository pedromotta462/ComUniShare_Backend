package com.example.ComUniShare.services.feedback;

import com.example.ComUniShare.domain.feedback.Feedback;
import com.example.ComUniShare.domain.feedback.FeedbackRequestDTO;
import com.example.ComUniShare.domain.feedback.FeedbackResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IfeedbackService {

    List<FeedbackResponseDTO> getAllFeedbacks();

    Feedback getFeedbackById(String feedbackId);

    ResponseEntity<String> createFeedback(FeedbackRequestDTO feed);

    void saveFeedback(Feedback feedback);

    void deleteFeedback(String feedbackId);

}
