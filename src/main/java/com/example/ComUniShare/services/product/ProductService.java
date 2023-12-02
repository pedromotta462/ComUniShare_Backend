package com.example.ComUniShare.services.product;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.product.ProductRequestDTO;
import com.example.ComUniShare.domain.product.ProductResponseDTO;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.repositories.ProductRepository;
import com.example.ComUniShare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IproductService {
    private final ProductRepository productRepository;

    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService){
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll().stream().map(ProductResponseDTO::new).toList();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public ResponseEntity<String> updateProduct(String productId, Map<String, Object> updates) {
        Product existingProduct = findById(productId);

        if (existingProduct == null) {
            return new ResponseEntity<>("Produto não encontrado com ID: " + productId, HttpStatus.NOT_FOUND);
        }

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            try {
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method[] methods = Product.class.getMethods();

                Method method = Arrays.stream(methods)
                        .filter(m -> m.getName().equals(methodName) && m.getParameterCount() == 1)
                        .findFirst()
                        .orElseThrow(NoSuchMethodException::new);

                Object convertedValue = convertValue(value, method.getParameterTypes()[0]);

                method.invoke(existingProduct, convertedValue);

                System.out.println("method:" + method + " methodname:" + methodName);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                return ResponseEntity.badRequest().body("Falha ao atualizar o campo: " + fieldName + " com o valor " + value + " \n " + e);
            }
        }

        saveProduct(existingProduct);

        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (targetType.equals(Integer.class) && value instanceof String) {
            return Integer.parseInt((String) value);
        } else if (targetType.isEnum() && value instanceof String) {
            // Converte para enumeração
            return Enum.valueOf((Class<? extends Enum>) targetType, (String) value);
        } else {
            return value;
        }
    }


    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> findProductsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByLogin(username);

        if (user != null) {
            return user.getProducts();
        } else {
            return List.of();
        }
    }

    @Override
    public ResponseEntity<String> createProductForUser(ProductRequestDTO prod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        User user = userService.findUserByLogin(currentUserId);
        Product product = new Product(prod);
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
