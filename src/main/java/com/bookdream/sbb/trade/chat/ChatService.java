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
    private SimpMessagingTemplate messagingTemplate;

    public List<Chat> getChatHistory(Long chatRoomId) {
        return chatRepository.findByChatRoomId(chatRoomId);
    }

    public Chat saveChat(Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        Chat savedChat = chatRepository.save(chat);

        // 새로운 메시지가 저장될 때 새로운 메시지 카운트를 업데이트합니다.
        updateNewMessagesCount(chat.getChatRoomId(), chat.getSenderId());

        // 채팅방의 마지막 메시지와 시간을 업데이트합니다.
        chatRoomRepository.findById(chat.getChatRoomId()).ifPresent(chatRoom -> {
            if (chat.getType() == Chat.MessageType.IMAGE) {
                chatRoom.setLastMessage("사진을 보냈습니다.");
            } else {
                chatRoom.setLastMessage(chat.getMessage());
            }
            chatRoom.setLastMessageTime(chat.getCreatedAt());
            chatRoom.setLastMessageSenderId(chat.getSenderId());
            chatRoomRepository.save(chatRoom);
        });

        return savedChat;
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
                    newChatRoom.setNewMessagesCountForSender(0);
                    newChatRoom.setNewMessagesCountForReceiver(0);
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

            messagingTemplate.convertAndSend("/topic/public", leaveMessage);

            if (chatRoom.getSenderId() == null && chatRoom.getReceiverId() == null) {
                chatRepository.deleteByChatRoomId(chatRoomId);
                chatRoomRepository.delete(chatRoom);
            }
        });
    }

    public void incrementNewMessagesCount(Long chatRoomId, String senderId, String currentUserId) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            if (currentUserId.equals(chatRoom.getSenderId())) {
                chatRoom.setNewMessagesCountForReceiver(chatRoom.getNewMessagesCountForReceiver() + 1);
            } else {
                chatRoom.setNewMessagesCountForSender(chatRoom.getNewMessagesCountForSender() + 1);
            }
            chatRoomRepository.save(chatRoom);
        });
    }

    public void resetNewMessagesCount(Long chatRoomId, String userId) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            if (userId.equals(chatRoom.getReceiverId())) {
                chatRoom.setNewMessagesCountForReceiver(0);
            } else {
                chatRoom.setNewMessagesCountForSender(0);
            }
            chatRoomRepository.save(chatRoom);
        });
    }

    private void updateNewMessagesCount(Long chatRoomId, String senderId) {
        chatRoomRepository.findById(chatRoomId).ifPresent(chatRoom -> {
            if (senderId.equals(chatRoom.getSenderId())) {
                chatRoom.setNewMessagesCountForReceiver(chatRoom.getNewMessagesCountForReceiver() + 1);
            } else {
                chatRoom.setNewMessagesCountForSender(chatRoom.getNewMessagesCountForSender() + 1);
            }
            chatRoomRepository.save(chatRoom);
        });
    }
    
    public int getTotalNewMessagesCount(String userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findBySenderIdOrReceiverId(userId, userId);
        return chatRooms.stream()
                        .mapToInt(chatRoom -> chatRoom.getNewMessagesCountForUser(userId))
                        .sum();
    }

}
