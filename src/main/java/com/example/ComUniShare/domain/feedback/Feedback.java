package com.example.ComUniShare.domain.feedback;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "feedbacks")
@Entity(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String comment;

    private int rating; // Pode ser uma escala de 1 a 5

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Feedback(String comment, int rating, User user, Product product) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.product = product;
        this.createdAt = new Date();
    }
}