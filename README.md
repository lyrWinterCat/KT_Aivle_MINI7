# 🚑 응급상황 인식 및 응급실 연계 서비스 포탈

## 📌 개요
본 프로젝트는 응급상황을 신속하게 인식하고, 적절한 응급실과 연계하는 웹 서비스 포탈입니다.  
자연어 처리(NLP) 및 음성 데이터를 활용하여 응급 여부를 판단하며, 네이버 지도 API를 사용해 가까운 응급실을 추천합니다.

---

## 🛠 사용 기술

### **📌 개발 도구**
- **IDE**: Visual Studio Code
- **CI/CD & 컨테이너화**: Docker
- **버전 관리**: Git, GitHub

### **📌 백엔드**
- **FastAPI (Python)**: 응급 상황 분석 및 AI 모델 서비스
- **Spring Boot (Java)**: 사용자 요청 처리 및 응급실 연계 시스템
- **SQLite**: 경량화된 데이터베이스 사용

### **📌 AI & 데이터 처리**
- **Hugging Face Transformers**: 자연어 처리 (NLP) 모델 활용
- **OpenAI API**: AI 기반 분석 기능 지원

### **📌 외부 API**
- **네이버 지도 API**: 위치 기반 응급실 추천

---

## 📂 프로젝트 구조
mini7_project/ 
├── mini7_06_fastapi/ # FastAPI 기반 AI 분석 서버 (Python) 
│ ├── db/ # 데이터베이스 관련 파일 (SQLite) 
│ ├── fine_tuned_bert/ # 파인튜닝된 BERT 모델 
│ ├── emergency.py # 응급 상황 판별 로직 
│ ├── main.py # FastAPI 엔트리 포인트 
│ ├── requirements.txt # Python 라이브러리 의존성 목록 
│ ├── summary_*.conf # 응급/비응급 판단 설정 파일 
│ ├── Dockerfile # FastAPI 컨테이너화 설정 
│── mini7_06_spring/ # Spring Boot 기반 웹 서비스 (Java) 
│ ├── src/ 
│ │ ├── main/ 
│ │ │ ├── java/com/ai4le/mini7/ 
│ │ │ │ ├── client/ # 외부 API 호출 
│ │ │ │ ├── controller/ # API 컨트롤러 
│ │ │ │ ├── dto/ # 데이터 전송 객체 
│ │ │ │ ├── model/ # 도메인 모델 
│ │ │ │ ├── repository/ # 데이터 액세스 계층 
│ │ │ │ ├── service/ # 비즈니스 로직 
│ │ │ │ ├── Mini7Application.java # Spring Boot 메인 실행 파일 
│ │ ├── resources/ 
│ │ │ ├── database/ # SQLite 데이터베이스 관련 파일 
│ │ │ ├── static/ # 정적 파일 
│ │ │ ├── templates/ # HTML 템플릿 (추후 확장 가능) 
│ │ │ ├── application.properties # Spring Boot 설정 파일 
│ │ ├── test/java/com/ai4le/mini7/ # 테스트 코드 
│ ├── Dockerfile # Spring Boot 컨테이너화 설정 
│ ├── build.gradle # Gradle 빌드 스크립트 
│ ├── settings.gradle # Gradle 설정 
│ │── README.md # 프로젝트 개요 및 문서


---

## 🔍 주요 기능

1. **응급 상황 인식 (NLP & 음성 분석)**
   - Hugging Face 기반 BERT 모델을 활용하여 응급 여부 판단
   - 음성 데이터 처리 후 텍스트 변환 및 분석

2. **응급실 연계 서비스**
   - 네이버 지도 API를 활용하여 위치 기반 응급실 추천
   - 사용자의 위치 정보를 기반으로 가장 가까운 병원 안내

3. **웹 서비스**
   - Spring Boot 백엔드를 활용한 API 서비스
   - FastAPI를 통한 AI 모델 서빙

4. **데이터 관리**
   - SQLite 기반 데이터 저장 및 관리

---

## 🚀 프로젝트 실행 방법

1️⃣ FastAPI 서버 실행 (AI 분석)**
```bash
cd mini7_06_fastapi
pip install -r requirements.txt
uvicorn main:app --reload

2️⃣ Spring Boot 서버 실행 (웹 서비스)
bash
복사
편집
cd mini7_06_spring
./gradlew bootRun
3️⃣ Docker 컨테이너 실행
bash
복사
편집
docker-compose up --build
🤝 기여 방법
이 레포지토리를 포크(Fork) 합니다.
새로운 브랜치를 생성합니다. (feature/새로운기능)
변경 사항을 커밋합니다. (git commit -m "추가된 기능 설명")
원격 저장소에 푸시합니다. (git push origin feature/새로운기능)
PR(Pull Request)을 생성하여 변경 사항을 공유합니다.
✨ 더 나은 응급 대응 시스템을 위한 기여를 환영합니다! 🚀

