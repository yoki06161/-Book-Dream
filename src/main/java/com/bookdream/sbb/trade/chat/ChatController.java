package com.bookdream.sbb.trade.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/trade/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/history")
    @ResponseBody
    public ResponseEntity<List<Chat>> getChatHistory(@RequestParam("chatRoomId") Long chatRoomId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).build();
        }
        List<Chat> chatHistory = chatService.getChatHistory(chatRoomId);
        return ResponseEntity.ok(chatHistory);
    }

    @GetMapping("/start")
    public String chatPage(@RequestParam("tradeIdx") int tradeIdx, @RequestParam("chatRoomId") Long chatRoomId, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/user/login";
        }

        String senderId = principal.getName();
        model.addAttribute("senderId", senderId);
        model.addAttribute("tradeIdx", tradeIdx);
        model.addAttribute("chatRoomId", chatRoomId);

        // 새로운 메시지 수 초기화
        chatService.resetNewMessagesCount(chatRoomId, senderId);

        return "trade/chat";
    }

    @GetMapping("/rooms")
    public String chatRooms(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/user/login";
        }

        String userId = principal.getName();
        List<ChatRoom> chatRooms = chatService.getChatRooms(userId);
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("userId", userId);
        return "trade/chat_rooms";
    }

    @PostMapping("/create")
    public String createChatRoom(@RequestParam("receiverId") String receiverId, @RequestParam("tradeIdx") int tradeIdx, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }

        String senderId = principal.getName();
        ChatRoom chatRoom = chatService.createChatRoom(senderId, receiverId, tradeIdx);
        return "redirect:/trade/chat/start?tradeIdx=" + tradeIdx + "&chatRoomId=" + chatRoom.getId();
    }

    @PostMapping("/leave")
    @ResponseBody
    public ResponseEntity<String> leaveChatRoom(@RequestParam("chatRoomId") Long chatRoomId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).build();
        }

        String userId = principal.getName();
        chatService.leaveChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("채팅방을 나갔습니다.");
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(Chat chatMessage, Principal principal) throws InterruptedException {
        // 이미지 메시지인 경우에만 1초 딜레이 추가
        if (chatMessage.getType() == Chat.MessageType.IMAGE) {
            Thread.sleep(1000);
        }

        chatMessage.setCreatedAt(LocalDateTime.now());
        chatService.saveChat(chatMessage);

        // 새로운 메시지 수 증가
        if (principal != null) {
            String currentUserId = principal.getName();
            if (!currentUserId.equals(chatMessage.getSenderId())) {
                chatService.incrementNewMessagesCount(chatMessage.getChatRoomId(), chatMessage.getSenderId(), currentUserId);
            }
        }

        // 채팅방 목록 갱신을 위해 새 메시지 이벤트 발행
        messagingTemplate.convertAndSend("/topic/chatRoomsUpdate", chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Chat addUser(Chat chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
        return chatMessage;
    }
}
