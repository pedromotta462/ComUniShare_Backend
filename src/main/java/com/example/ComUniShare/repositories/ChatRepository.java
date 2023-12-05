package com.example.ComUniShare.repositories;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<Chat> findAllByUser1OrUser2(User user1, User user2);

    Optional<Chat> findByUser1AndUser2(User user1, User user2);
}
