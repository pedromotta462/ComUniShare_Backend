// ChatMessageController.java
package com.example.ComUniShare.controllers;

import com.example.ComUniShare.domain.chat.chatmessage.ChatMessageRequestDTO;
import com.example.ComUniShare.domain.chat.chatmessage.ChatMessageResponseDTO;
import com.example.ComUniShare.services.chat.chatmessage.ChatMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    //ok
    @GetMapping("/{chatId}")
    public ResponseEntity<List<ChatMessageResponseDTO>> getChatMessagesForChat(@PathVariable String chatId) {
        List<ChatMessageResponseDTO> chatMessagesList = chatMessageService.getChatMessagesForChat(chatId);
        if (chatMessagesList != null) {
            return ResponseEntity.ok(chatMessagesList);
        }
        return ResponseEntity.notFound().build();
    }

    //ok
    @PostMapping
    public ResponseEntity<String> sendChatMessage(@RequestBody @Valid ChatMessageRequestDTO chatMessageRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body("Validation failed: " + errors.toString());
        }

        return chatMessageService.sendChatMessage(chatMessageRequestDTO.chatId(), chatMessageRequestDTO.message());
    }
}
