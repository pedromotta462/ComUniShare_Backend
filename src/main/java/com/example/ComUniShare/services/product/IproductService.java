package com.example.ComUniShare.services.product;

import com.example.ComUniShare.domain.product.Product;

import java.util.List;

public interface IproductService {
    Product findById(String productId);

    List<Product> findAllProducts();

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(String productId);

    List<Product> findProductsByUser();
}


