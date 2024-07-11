package com.bookdream.sbb.trade.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/trade/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/history")
    @ResponseBody
    public ResponseEntity<List<Chat>> getChatHistory(@RequestParam("senderId") String senderId, @RequestParam("receiverId") String receiverId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(chatService.getChatHistory(senderId, receiverId));
    }

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<Chat> sendChat(@RequestBody Chat chat, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).build();
        }
        chat.setCreatedAt(LocalDateTime.now());
        Chat savedChat = chatService.saveChat(chat);
        // 추가적으로 상대방에게 알림을 보내는 로직을 여기에 추가할 수 있습니다.
        return ResponseEntity.ok(savedChat);
    }

    @GetMapping("/start")
    public String chatPage(@RequestParam("receiverId") String receiverId, @RequestParam("tradeIdx") int tradeIdx, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        
        String senderId = principal.getName();
        model.addAttribute("senderId", senderId);
        model.addAttribute("receiverId", receiverId);
        model.addAttribute("tradeIdx", tradeIdx);
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
        return "trade/chat_rooms";
    }

    @PostMapping("/create")
    public String createChatRoom(@RequestParam("receiverId") String receiverId, @RequestParam("tradeIdx") int tradeIdx, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        
        String senderId = principal.getName();
        ChatRoom chatRoom = chatService.createChatRoom(senderId, receiverId, tradeIdx);
        return "redirect:/trade/chat/start?receiverId=" + receiverId + "&tradeIdx=" + tradeIdx;
    }
    
    @PostMapping("/leave")
    public ResponseEntity<String> leaveChatRoom(@RequestParam("receiverId") String receiverId, @RequestParam("tradeIdx") int tradeIdx, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).build();
        }

        String senderId = principal.getName();
        chatService.deleteChatRoom(senderId, receiverId, tradeIdx);
        return ResponseEntity.ok("채팅방을 나갔습니다.");
    }
}
