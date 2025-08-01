package com.example.chatbot_practice.controller.request;

import java.time.LocalDate;

public record AIRequest(
        LocalDate date,
        String content
) {}
