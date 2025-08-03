package com.example.chatbot_practice.service;

import com.example.chatbot_practice.controller.request.ChatRequest;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;


@Service
public class AIService {

    private final ChatModel chatModel;

    public AIService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getChatResponse(ChatRequest chatRequest) {

        String typeDescription = """
        다음은 8가지 음식 취향 유형의 정의입니다. 식당 리뷰를 보고 어떤 유형의 사람들이 그 식당을 선호할지 판단해 주세요.

        1. 고독한 미식가형: 혼밥 선호, 조용한 분위기, 음식 자체의 맛 중시, SNS 사용 거의 없음.
        2. 아재 입맛형: 익숙한 맛, 정겨운 한식, 가격과 맛을 균형 있게 고려, 투박한 식당 선호.
        3. 파티피플형: 줄 서는 집, 인스타 인증샷, 트렌디한 장소, 시끄러워도 분위기 우선.
        4. 가성비 인간형: 저렴하고 양 많고 만족도 높은 곳, 프랜차이즈나 체인점도 선호.
        5. 감성 브런치러형: 브런치, 예쁜 인테리어, 감성 분위기와 비주얼 음식, 사진 찍기 좋은 곳.
        6. 리뷰 분석가형: 음식점 선택 시 리뷰를 꼼꼼히 읽고, 먹은 후에도 리뷰를 남김. 객관적 분석을 즐김.
        7. 숨은 맛집 헌터형: 알려지지 않은 로컬 맛집 탐방, 유니크한 맛집 발견 즐김.
        8. 효율 실속형: 합리적 가격, 맛, 친절, 위생 등 종합적으로 실속을 중시하는 사람.

        식당 설명을 기반으로 1~2개 유형을 골라서 제시해 주세요. 어떤 유형에 적합한지만 제시해줘, 이유는 주지 않아도 돼.
        """;

        // 리뷰를 합쳐서 프롬프트 생성
        String promptText = typeDescription + "\n\n[식당 리뷰]\n" + chatRequest.getReview();

        // Spring AI ChatModel로 호출
        String response = chatModel.call(new Prompt(promptText))
                .getResult()
                .getOutput()
                .getText();

        return response;
    }
}

//    public String getChatResponse(ChatRequest chatRequest) {
//
//        String response = webClient.post()
//                .uri("/chat")
//                .header("Content-Type", "application/json")
//                .bodyValue(chatRequest)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return response;
//    }
