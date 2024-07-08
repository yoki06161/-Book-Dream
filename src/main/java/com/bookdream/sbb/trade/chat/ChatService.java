package com.bookdream.sbb.trade.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//패키지와 임포트 생략
@Service
public class ChatService {
 @Autowired
 private ChatRepository chatRepository;

 public List<Chat> getChatHistory(Long senderId, Long receiverId) {
     return chatRepository.findBySenderIdAndReceiverId(senderId, receiverId);
 }

 public Chat saveChat(Chat chat) {
     chat.setCreatedAt(LocalDateTime.now());
     return chatRepository.save(chat);
 }
}

