import os
import requests
from bs4 import BeautifulSoup
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain.agents import initialize_agent, Tool
from langchain.agents.agent_types import AgentType
from dotenv import load_dotenv

load_dotenv()

def search_with_urls(query: str, num_results=5):
    params = {
        "q": query,
        "api_key": os.getenv("SERPAPI_API_KEY"),
        "engine": "google_maps",
        "num": num_results
    }
    res = requests.get("https://serpapi.com/search", params=params)
    data = res.json()

    print("[DEBUG] 검색 결과:", data.get("organic_results", []))

    results = data.get("organic_results", [])
    return [(r.get("title", ""), r.get("link", "")) for r in results]


# 2. 해당 URL에서 본문 추출
def extract_text_from_url(url: str, max_length=2000):
    headers = {"User-Agent": "Mozilla/5.0"}
    try:
        response = requests.get(url, headers=headers, timeout=10)
        soup = BeautifulSoup(response.text, "html.parser")
        text = soup.get_text(separator="\n")
        return text.strip()[:max_length]
    except Exception as e:
        return f"(크롤링 실패: {e})"

# 3. 검색 + 크롤링 전체 흐름
def query_reviews(query: str, count: int = 3) -> str:
    results = search_with_urls(query, count)
    response = ""
    for title, url in results:
        content = extract_text_from_url(url)
        response += f"🔗 [{title}]({url})\n{content[:300]}...\n\n"
    return response

# 4. LangChain Agent 구성
llm = ChatGoogleGenerativeAI(
    model="gemini-2.5-flash",
    temperature=0.0,
    google_api_key=os.getenv("GOOGLE_API_KEY")
)

tools = [
    Tool(
        name="맛집 블로그 리뷰 크롤러",
        func=query_reviews,
        description="검색어로 맛집 리뷰를 찾고 요약하며 URL을 포함해 보여줍니다."
    )
]

agent = initialize_agent(
    tools=tools,
    llm=llm,
    agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    verbose=True
)

def run_chat(message: str) -> str:
    return agent.run(message)

# llm = ChatGoogleGenerativeAI(
#     model="gemini-2.5-flash",
#     temperature=0.0,
#     google_api_key=os.getenv("GOOGLE_API_KEY")
# )

# search = SerpAPIWrapper(serpapi_api_key=os.getenv("SERPAPI_API_KEY"))

# tools = [
#     Tool(
#         name="Search",
#         func=search.run,
#         description="웹 검색을 통해 정보를 찾는 도구"
#     )
# ]

# agent = initialize_agent(
#     tools=tools,
#     llm=llm,
#     agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
#     verbose=True
# )

# def run_chat(message: str) -> str:
#     return agent.run(message)
