package com.example.chatbot_practice.service;

import com.example.chatbot_practice.controller.request.AIRequest;
import com.example.chatbot_practice.controller.request.ChatRequest;
import com.example.chatbot_practice.controller.response.AIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AIService {

    private final WebClient webClient;

    public AIService() {
        this.webClient = WebClient.create("http://localhost:8000");
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getChatResponse(ChatRequest chatRequest) {

        String response = webClient.post()
                .uri("/chat")
                .header("Content-Type", "application/json")
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .map(ChatResponse::getResponse)
                .block();

        return response;
    }

    public static class ChatResponse {
        private String response;
        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }
    }
}
