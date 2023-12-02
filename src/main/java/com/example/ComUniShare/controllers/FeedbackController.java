package com.example.ComUniShare.controllers;

import com.example.ComUniShare.domain.feedback.Feedback;
import com.example.ComUniShare.domain.feedback.FeedbackRequestDTO;
import com.example.ComUniShare.domain.feedback.FeedbackResponseDTO;
import com.example.ComUniShare.services.feedback.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    //ok
    @GetMapping
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedbacks() {
        List<FeedbackResponseDTO> feedbacksList = feedbackService.getAllFeedbacks();

        return ResponseEntity.ok(feedbacksList);
    }

    //ok
    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackResponseDTO> getFeedbackById(@PathVariable String feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);

        if (feedback != null) {
            return ResponseEntity.ok(new FeedbackResponseDTO(feedback));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //ok
    @PostMapping
    public ResponseEntity<String> createFeedback(@RequestBody @Valid FeedbackRequestDTO feedbackRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body("Validation failed: " + errors.toString());
        }
        return feedbackService.createFeedback(feedbackRequestDTO);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable String feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        if (feedback == null) {
            return ResponseEntity.notFound().build();
        }
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback excluído com sucesso!");
    }

}
