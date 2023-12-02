package com.example.ComUniShare.services.feedback;

import com.example.ComUniShare.domain.feedback.Feedback;
import com.example.ComUniShare.domain.feedback.FeedbackRequestDTO;
import com.example.ComUniShare.domain.feedback.FeedbackResponseDTO;
import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.repositories.FeedbackRepository;
import com.example.ComUniShare.services.product.ProductService;
import com.example.ComUniShare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService implements IfeedbackService{

    @Autowired
    private  FeedbackRepository feedbackRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private  ProductService productService;


    @Override
    public List<FeedbackResponseDTO> getAllFeedbacks() {
        return feedbackRepository.findAll().stream().map(FeedbackResponseDTO::new).toList();
    }

    @Override
    public Feedback getFeedbackById(String feedbackId) {
        return feedbackRepository.findById(feedbackId).orElse(null);
    }

    @Override
    public ResponseEntity<String> createFeedback(FeedbackRequestDTO feed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        User user = userService.findUserByLogin(currentUserId);

        Product product = productService.findById(feed.productId());

        saveFeedback(new Feedback(
                feed.comment(),
                feed.rating(),
                user,
                product
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body("Feedback criado com sucesso!");
    }


    @Override
    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(String feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
