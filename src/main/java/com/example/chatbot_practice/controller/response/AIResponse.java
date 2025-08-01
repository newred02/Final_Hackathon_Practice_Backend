package com.example.chatbot_practice.controller.response;

public record AIResponse(
        boolean result,
        boolean hasTime,
        String datetime,
        String content
) {}
