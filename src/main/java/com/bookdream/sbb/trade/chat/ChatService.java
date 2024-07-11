package com.bookdream.sbb.trade.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<Chat> getChatHistory(String userId, String otherUserId) {
        return chatRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(userId, otherUserId, otherUserId, userId);
    }

    public Chat saveChat(Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    public List<ChatRoom> getChatRooms(String userId) {
        return chatRoomRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    public ChatRoom createChatRoom(String senderId, String receiverId, int tradeIdx) {
        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndReceiverIdAndTradeIdx(senderId, receiverId, tradeIdx)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setSenderId(senderId);
                    newChatRoom.setReceiverId(receiverId);
                    newChatRoom.setTradeIdx(tradeIdx);
                    newChatRoom.setLastMessageTime(LocalDateTime.now());
                    return chatRoomRepository.save(newChatRoom);
                });
        return chatRoom;
    }
    
    public void deleteChatRoom(String senderId, String receiverId, int tradeIdx) {
        chatRoomRepository.findBySenderIdAndReceiverIdAndTradeIdx(senderId, receiverId, tradeIdx)
                .ifPresent(chatRoomRepository::delete);
    }
}
