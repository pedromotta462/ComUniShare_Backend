package com.example.ComUniShare.services.product;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.repositories.ProductRepository;
import com.example.ComUniShare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class ProductService implements IproductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> findProductsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Use o serviço UserService para obter o usuário pelo nome de usuário
        User user = userService.findUserByLogin(username);

        // Verifique se o usuário foi encontrado
        if (user != null) {
            // Obtenha a lista de produtos associados a esse usuário
            return user.getProducts();
        } else {
            // Retorne uma lista vazia se o usuário não for encontrado
            return List.of();
        }
    }

    public ResponseEntity<String> createProductForUser(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        User user = userService.findUserByLogin(currentUserId);
        if (user != null) {
            product.setUser(user);
            user.getProducts().add(product);
            userService.updateUser(user);
            saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto criado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com ID: " + currentUserId);
        }
    }
}
