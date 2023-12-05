// ChatController.java
package com.example.ComUniShare.controllers;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.chat.ChatRequestDTO;
import com.example.ComUniShare.domain.chat.ChatResponseDTO;
import com.example.ComUniShare.services.chat.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //ok
    @GetMapping
    public ResponseEntity<List<ChatResponseDTO>> getAllChatsForUser() {
        List<ChatResponseDTO> chatsList = chatService.getAllChatsForUser();
        return ResponseEntity.ok(chatsList);
    }

    //ok
    @PostMapping
    public ResponseEntity<String> getOrCreateChat(@RequestBody @Valid ChatRequestDTO chatRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body("Validation failed: " + errors.toString());
        }
        return chatService.getOrCreateChat(chatRequestDTO);
    }

    //ok
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponseDTO> getChatById(@PathVariable String chatId) {
        Chat chat = chatService.getChatById(chatId);
        if(chat != null) {
            return ResponseEntity.ok(new ChatResponseDTO(chat));
        }
        return ResponseEntity.notFound().build();
    }

    //ok
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChatById(@PathVariable String chatId) {
        return chatService.deleteChatById(chatId);
    }
}
