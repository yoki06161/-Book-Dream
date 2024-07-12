package com.bookdream.sbb.trade.chat;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderId;
    private String receiverId;
    private int tradeIdx;
    private String bookTitle;
    private LocalDateTime lastMessageTime;

    public String getChatRoomName() {
        return senderId + " & " + receiverId + " & " + tradeIdx;
    }
}
