package com.example.ComUniShare.services.chat.chatmessage;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.chat.ChatResponseDTO;
import com.example.ComUniShare.domain.chat.chatmessage.ChatMessage;
import com.example.ComUniShare.domain.chat.chatmessage.ChatMessageResponseDTO;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.repositories.ChatMessageRepository;
import com.example.ComUniShare.services.chat.ChatService;
import com.example.ComUniShare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService implements IchatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatService chatService;

    private final UserService userService;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatService chatService, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatService = chatService;
        this.userService = userService;
    }

    @Override
    public List<ChatMessageResponseDTO> getChatMessagesForChat(String chatId) {
        Chat chat = chatService.getChatById(chatId);
        if(chat != null){
            return chatMessageRepository.findAllByChat(chat).stream().map(ChatMessageResponseDTO::new).toList();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> sendChatMessage(String chatId, String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User sender = userService.findUserByLogin(username);

        Chat chat = chatService.getChatById(chatId);

        if(chat != null){
            ChatMessage chatMessage = new ChatMessage(chat, sender, message);
            chatMessageRepository.save(chatMessage);
            return ResponseEntity.status(HttpStatus.CREATED).body("Mensagem enviada com sucesso!");
        }   else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat não encontrado com ID: " + chatId);
        }
    }

}


