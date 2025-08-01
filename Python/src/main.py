from fastapi import FastAPI
from pydantic import BaseModel
from dotenv import load_dotenv
import os

from langchain_google_genai import ChatGoogleGenerativeAI
from langchain.schema import HumanMessage
from searchtool import run_chat

load_dotenv()

app = FastAPI()

class ChatRequest(BaseModel):
    message: str

# # Gemini 모델 설정
# llm = ChatGoogleGenerativeAI(
#     model="gemini-2.5-flash",  # 또는 "gemini-pro"
#     temperature=0.0,
#     google_api_key=os.getenv("GOOGLE_API_KEY")
# )

@app.post("/chat")
async def chat(req: ChatRequest):
    response = run_chat(req.message)
    return {"response": response}
