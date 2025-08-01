package com.example.chatbot_practice.controller;

import com.example.chatbot_practice.controller.request.ChatRequest;
import com.example.chatbot_practice.service.AIService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class ChatController {

    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/chat")
    public String chat(@RequestBody ChatRequest chatRequest) {
        String response = aiService.getChatResponse(chatRequest);
        return response;
    }
}
