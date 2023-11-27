package com.example.ComUniShare.controllers;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.product.ProductResponseDTO;
import com.example.ComUniShare.repositories.ProductRepository;
import com.example.ComUniShare.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    ProductRepository repository;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        Product product = productService.findById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        List<ProductResponseDTO> productList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();

        return ResponseEntity.ok(productList);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCurrentUser() {
        List<Product> products = productService.findProductsByUser();
        List<ProductResponseDTO> productDTOs = products.stream().map(ProductResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProductForUser(@RequestBody Product product) {
        ResponseEntity<String> response = productService.createProductForUser(product);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable String productId, @RequestBody Product product) {
        product.setId(productId);
        productService.updateProduct(product);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Produto excluído com sucesso!");
    }
}
