package com.example.chatbot_practice.service;

import com.example.chatbot_practice.controller.request.ChatRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
