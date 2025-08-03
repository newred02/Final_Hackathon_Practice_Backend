from fastapi import FastAPI
from pydantic import BaseModel
from dotenv import load_dotenv
import os

from langchain_google_genai import ChatGoogleGenerativeAI
from langchain.schema import HumanMessage

load_dotenv()

app = FastAPI()

class ReviewRequest(BaseModel):
    review: str

# 🔸 Gemini 모델 설정
llm = ChatGoogleGenerativeAI(
    model="gemini-1.5-flash",  # 또는 gemini-pro
    temperature=0.3,
    google_api_key=os.getenv("GOOGLE_API_KEY")
)

# 🔸 유형 설명 (프롬프트)
TYPE_DESCRIPTION = """
다음은 8가지 음식 취향 유형의 정의입니다. 식당 리뷰를 보고 어떤 유형의 사람들이 그 식당을 선호할지 판단해 주세요.

1. 🍚 고독한 미식가형: 혼밥 선호, 조용한 분위기, 음식 자체의 맛 중시, SNS 사용 거의 없음.
2. 👴 아재 입맛형: 익숙한 맛, 정겨운 한식, 가격과 맛을 균형 있게 고려, 투박한 식당 선호.
3. 🕺 파티피플형: 줄 서는 집, 인스타 인증샷, 트렌디한 장소, 시끄러워도 분위기 우선.
4. 💸 가성비 인간형: 저렴하고 양 많고 만족도 높은 곳, 프랜차이즈나 체인점도 선호.
5. 👀 감성 브런치러형: 브런치, 예쁜 인테리어, 감성 분위기와 비주얼 음식, 사진 찍기 좋은 곳.
6. 🤓 리뷰 분석가형: 음식점 선택 시 리뷰를 꼼꼼히 읽고, 먹은 후에도 리뷰를 남김. 객관적 분석을 즐김.
7. ⛺️ 숨은 맛집 헌터형: 알려지지 않은 로컬 맛집 탐방, 유니크한 맛집 발견 즐김.
8. 🧠 효율 실속형: 합리적 가격, 맛, 친절, 위생 등 종합적으로 실속을 중시하는 사람.

식당 설명을 기반으로 1~2개 유형을 골라서 제시해 주세요 어떤 유형에 적합한지만 제시해줘 이유는 주지 않아도 돼.
"""

# 🔸 API 엔드포인트 정의
@app.post("/chat")
async def analyze_review(req: ReviewRequest):
    review_prompt = f"{TYPE_DESCRIPTION}\n\n[식당 리뷰]\n{req.review.strip()}"
    response = llm([HumanMessage(content=review_prompt)])
    return {"type_analysis": response.content}
