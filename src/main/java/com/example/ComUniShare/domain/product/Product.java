package com.example.ComUniShare.domain.product;

import jakarta.persistence.*;
import lombok.*;
import com.example.ComUniShare.domain.user.User;

import java.math.BigInteger;

@Table(name = "products")
@Entity(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Product(ProductRequestDTO data){
        this.name = data.name();
        this.description = data.description();
        this.type = ProductType.valueOf(data.type().toUpperCase());
        this.price = data.price();
    }
}
