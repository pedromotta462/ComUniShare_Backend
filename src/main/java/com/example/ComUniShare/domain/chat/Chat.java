package com.example.ComUniShare.domain.chat;

import com.example.ComUniShare.domain.chat.chatmessage.ChatMessage;
import com.example.ComUniShare.domain.user.User;
import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "chats")
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    private User user2;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> messages = new ArrayList<>();

    public Chat(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
