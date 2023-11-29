package com.example.ComUniShare.controllers;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.product.ProductRequestDTO;
import com.example.ComUniShare.domain.product.ProductResponseDTO;
import com.example.ComUniShare.repositories.ProductRepository;
import com.example.ComUniShare.services.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    ProductRepository repository;

    //ok
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable String productId) {
        Product product = productService.findById(productId);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    //ok
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> productList = productService.findAllProducts();

        return ResponseEntity.ok(productList);
    }

    //ok
    @GetMapping("/user")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCurrentUser() {
        List<Product> products = productService.findProductsByUser();
        List<ProductResponseDTO> productDTOs = products.stream().map(ProductResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    //ok
    @PostMapping("/create")
    public ResponseEntity<String> createProductForUser(@RequestBody @Valid ProductRequestDTO product) {
        ResponseEntity<String> response = productService.createProductForUser(product);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    //ok, só n atualiza o type pq é enum
    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable String productId, @RequestBody Map<String, Object> updates) {
        return productService.updateProduct(productId, updates);
    }

    //ok
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Produto excluído com sucesso!");
    }
}
