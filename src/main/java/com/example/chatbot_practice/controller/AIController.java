package com.example.chatbot_practice.controller;

import com.example.chatbot_practice.controller.request.AIRequest;
import com.example.chatbot_practice.controller.response.AIResponse;
import com.example.chatbot_practice.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

//    @GetMapping("/")
//    public AIResponse extractTime(
//            @RequestParam int year,
//            @RequestParam int month,
//            @RequestParam int day,
//            @RequestParam String content
//    ){
//        LocalDate date = LocalDate.of(year, month, day);
//        AIRequest request = new AIRequest(date, content);
//        return aiService.extractTime(request);
//    }
}
