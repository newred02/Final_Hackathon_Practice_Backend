package com.example.chatbot_practice;

import com.example.chatbot_practice.controller.request.AIRequest;
import com.example.chatbot_practice.controller.response.AIResponse;
import com.example.chatbot_practice.service.AIService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ChatbotPracticeApplicationTests {

    @Test
    void contextLoads() {
    }

}
