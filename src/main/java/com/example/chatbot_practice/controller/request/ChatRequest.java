package com.example.chatbot_practice.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
    private String review;

    public ChatRequest() {}

    public ChatRequest(String review) {
        this.review = review;
    }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
}
