package com.example.ComUniShare.controllers;

import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.domain.user.UserResponseDTO;
import com.example.ComUniShare.services.user.UserService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //ok
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String userId) {
        User user = userService.findById(userId);

        if (user != null) {
            UserResponseDTO userDTO = new UserResponseDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //ok
    @GetMapping("/login/{login}")
    public ResponseEntity<UserResponseDTO> getUserByName(@PathVariable String login) {
        User user = userService.findUserByLogin(login);

        if (user != null) {
            UserResponseDTO userDTO = new UserResponseDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //ok
    @GetMapping("/mydata")
    public ResponseEntity<UserResponseDTO> getMyData() {
        User user = userService.myData();

        if (user != null) {
            UserResponseDTO userDTO = new UserResponseDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //ok
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserResponseDTO> userDTOs = users.stream().map(UserResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    //ok
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody Map<String, Object> updates) {
        return userService.updateUserAdapted(userId, updates);
    }

    //ok
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>("Usuário não encontrado com ID: " + userId, HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("Usuário excluído com sucesso!");
    }
}
