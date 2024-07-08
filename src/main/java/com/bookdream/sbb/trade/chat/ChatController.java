package com.bookdream.sbb.trade.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/history")
    public ResponseEntity<List<Chat>> getChatHistory(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(chatService.getChatHistory(senderId, receiverId));
    }

    @PostMapping("/send")
    public ResponseEntity<Chat> sendChat(@RequestBody Chat chat) {
        return ResponseEntity.ok(chatService.saveChat(chat));
    }
    
    @GetMapping
    public String chatPage(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId, Model model) {
        model.addAttribute("senderId", senderId);
        model.addAttribute("receiverId", receiverId);
        return "chat";
    }
}
