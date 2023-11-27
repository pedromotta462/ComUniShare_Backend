package com.example.ComUniShare.repositories;

import com.example.ComUniShare.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
