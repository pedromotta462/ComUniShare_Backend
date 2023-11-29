package com.example.ComUniShare.services.product;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.product.ProductResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IproductService {
    Product findById(String productId);

    List<ProductResponseDTO> findAllProducts();

    void saveProduct(Product product);

    ResponseEntity<String> updateProduct(String productId, Map<String, Object> updates);

    void deleteProduct(String productId);

    List<Product> findProductsByUser();
}


