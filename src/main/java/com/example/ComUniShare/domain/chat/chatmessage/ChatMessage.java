package com.example.ComUniShare.domain.chat.chatmessage;

import com.example.ComUniShare.domain.chat.Chat;
import com.example.ComUniShare.domain.user.User;
import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "chat_messages")
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt = new Date();

    public ChatMessage(Chat chat, User sender, String message) {
        this.chat = chat;
        this.sender = sender;
        this.message = message;
    }
}
