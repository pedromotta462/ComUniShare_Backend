package com.example.ComUniShare.controllers;


import com.example.ComUniShare.domain.user.AuthenticationDTO;
import com.example.ComUniShare.domain.user.LoginResponseDTO;
import com.example.ComUniShare.domain.user.RegisterDTO;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.infra.security.TokenService;
import com.example.ComUniShare.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            // Retorna um BadRequest (status 400) com mensagens de erro no corpo
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            // Se a autenticação falhar, você pode lidar com isso aqui
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            // Retorna um BadRequest (status 400) com mensagens de erro no corpo
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // Verifica se o login já está em uso
        if (this.repository.findByLogin(data.login()) != null) {
            // Retorna um BadRequest (status 400) com mensagem personalizada
            return ResponseEntity.badRequest().body("Login já em uso. Escolha outro login.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword, data.fone(), data.address(), data.role());

        this.repository.save(newUser);

        // Retorna um Created (status 201) indicando que o recurso foi criado com sucesso
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
