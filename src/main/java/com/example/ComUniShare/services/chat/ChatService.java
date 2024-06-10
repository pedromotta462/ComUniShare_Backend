package com.example.ComUniShare.services.chat;


import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.chat.ChatRequestDTO;
import com.example.ComUniShare.domain.chat.ChatResponseDTO;
import com.example.ComUniShare.domain.feedback.FeedbackResponseDTO;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.repositories.ChatRepository;
import com.example.ComUniShare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements IchatService {

    private final ChatRepository chatRepository;

    private final UserService userService;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public List<ChatResponseDTO> getAllChatsForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByLogin(username);
        return chatRepository.findAllByUser1OrUser2(user, user).stream().map(ChatResponseDTO::new).toList();
    }

    @Override
    public ResponseEntity<String> getOrCreateChat(ChatRequestDTO user2Id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user1 = userService.findUserByLogin(username);
        User user2 = userService.findById(user2Id.user2Id());

        if(user2 != null){
            chatRepository.findByUser1AndUser2(user1, user2)
                    .orElseGet(() -> chatRepository.save(new Chat(user1, user2)));

            return ResponseEntity.status(HttpStatus.CREATED).body("Chat iniciado com sucesso!");
        }   else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com ID: " + user2Id);
        }
    }

    @Override
    public Chat getChatById(String chatId) {
        return chatRepository.findById(chatId)
                .orElse(null);
    }

    @Override
    public ResponseEntity<String> deleteChatById(String chatId) {
        Chat chat = getChatById(chatId);
        if(chat != null) {
            chatRepository.deleteById(chatId);
            return ResponseEntity.status(HttpStatus.OK).body("Chat excluido com sucesso!");
        }   else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat não encontrado com ID: " + chatId);
        }
    }

}
