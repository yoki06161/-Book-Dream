package com.bookdream.sbb.trade.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(String senderId1, String receiverId1, String receiverId2, String senderId2);
    List<Chat> findByChatRoomId(Long chatRoomId);
}
