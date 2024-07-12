package com.bookdream.sbb.trade.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookdream.sbb.trade.TradeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // STOMP 메시지 전송을 위한 템플릿

    public List<Chat> getChatHistory(Long chatRoomId) {
        return chatRepository.findByChatRoomId(chatRoomId);
    }

    public Chat saveChat(Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    public List<ChatRoom> getChatRooms(String userId) {
        return chatRoomRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    public ChatRoom createChatRoom(String senderId, String receiverId, int tradeIdx) {
        String bookTitle = tradeService.getTradeById(tradeIdx).getTitle();

        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndReceiverIdAndTradeIdx(senderId, receiverId, tradeIdx)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setSenderId(senderId);
                    newChatRoom.setReceiverId(receiverId);
                    newChatRoom.setTradeIdx(tradeIdx);
                    newChatRoom.setBookTitle(bookTitle);
                    newChatRoom.setLastMessageTime(LocalDateTime.now());
                    return chatRoomRepository.save(newChatRoom);
                });
        return chatRoom;
    }

    @Transactional
    public void leaveChatRoom(Long chatRoomId, String userId) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            boolean isSender = userId.equals(chatRoom.getSenderId());
            boolean isReceiver = userId.equals(chatRoom.getReceiverId());

            if (isSender) {
                chatRoom.setSenderId(null);
            } else if (isReceiver) {
                chatRoom.setReceiverId(null);
            }

            chatRoomRepository.save(chatRoom);

            Chat leaveMessage = new Chat();
            leaveMessage.setSenderId(userId);
            leaveMessage.setMessage("상대방이 나갔습니다.");
            leaveMessage.setChatRoomId(chatRoomId);
            leaveMessage.setType(Chat.MessageType.LEAVE);
            chatRepository.save(leaveMessage);

            // STOMP 메시지 전송
            messagingTemplate.convertAndSend("/topic/public", leaveMessage);

            if (chatRoom.getSenderId() == null && chatRoom.getReceiverId() == null) {
                chatRepository.deleteByChatRoomId(chatRoomId);
                chatRoomRepository.delete(chatRoom);
            }
        });
    }

    public void incrementNewMessagesCount(Long chatRoomId) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            chatRoom.incrementNewMessagesCount();
            chatRoomRepository.save(chatRoom);
        });
    }

    public void resetNewMessagesCount(Long chatRoomId) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            chatRoom.resetNewMessagesCount();
            chatRoomRepository.save(chatRoom);
        });
    }
}
