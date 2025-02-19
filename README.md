# 🚑 응급상황 인식 및 응급실 연계 서비스 포탈

## 📌 개요
본 프로젝트는 **응급상황을 신속하게 인식하고, 적절한 응급실과 연계하는 웹 서비스 포탈**입니다.  
자연어 처리(NLP) 및 음성 데이터를 활용하여 응급 여부를 판단하며, 네이버 지도 API를 사용해 가까운 응급실을 추천합니다.

---

## 🎯 프로젝트 목표
✅ 응급 요청에 적합한 **응급실 연계 서비스 포탈 구축**  
✅ 사용자의 **위치 및 음성 데이터**를 기반으로 병원 목록 제공  
✅ **AI 기반 응급상황 판단** 및 **실시간 응급실 추천 시스템 구현**  

---

## 🎬 시연 영상
[![시연 영상](https://raw.githubusercontent.com/lyrWinterCat/KT_Aivle_MINI7/main/EmergencyReport.png)](https://www.youtube.com/watch?v=bYSpKPcFpw0)

---

## 🛠 사용 기술

### **📌 개발 도구**
- **IDE**: Visual Studio Code, IntelliJ
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

## 🏗 시스템 구성도

### 🔹 **시스템 전체 구조**
본 프로젝트는 **Spring Boot + FastAPI**를 활용한 **마이크로서비스 아키텍처**로 설계되었습니다.
📌 브라우저에서 음성 및 위치 데이터 입력 <br>
📌 프론트엔드(Spring Boot)에서 데이터 수집 및 요청 전달<br> 
📌 백엔드(Spring Boot + FastAPI)에서 응급 여부 판별 및 병원 추천<br> 
📌 FastAPI를 활용한 AI 분석 및 데이터 처리<br> 
📌 네이버 지도 API를 이용한 응급실 추천<br> 
📌 Docker 컨테이너를 이용한 서비스 배포<br>

### 🖥 **구성 요소**
#### 1️⃣ **사용자 입력**
- 사용자는 **브라우저를 통해 음성 메시지와 위치 정보를 입력**  
- 입력된 메시지는 "**다쳤어요!**", "**위급해요!**" 등의 긴급 상황을 포함  

#### 2️⃣ **프론트엔드 (Spring Boot)**
- **View Template Engine** 기반으로 UI 구성
- **Spring Boot**를 사용하여 백엔드와 통신
- **기능**: 
  - 음성 및 위치 데이터 수집  
  - 추천된 응급실 리스트 제공  
- **배포**: **Docker를 이용한 컨테이너화**

#### 3️⃣ **백엔드 (Spring Boot + FastAPI)**
- **Spring Boot (Java)**
  - 프론트엔드와 통신하는 역할
  - 사용자의 요청을 **FastAPI**로 전달
  - FastAPI에서 받은 응급실 데이터를 **프론트엔드로 반환**
- **FastAPI (Python)**
  - REST API 기반으로 **음성 인식, 텍스트 요약 및 분류 처리**
  - AI 모델(BERT, Whisper, ChatGPT)을 활용한 응급 판단

---

## 📂 프로젝트 구조
mini7_project/ <br>
├── mini7_06_fastapi/ # FastAPI 기반 AI 분석 서버 (Python) <br>
│ ├── db/ # SQLite 데이터베이스 파일 <br>
│ ├── fine_tuned_bert/ # 파인튜닝된 BERT 모델 <br>
│ ├── emergency.py # 응급 상황 판별 로직 <br>
│ ├── main.py # FastAPI 엔트리 포인트 <br>
│ ├── requirements.txt # Python 라이브러리 의존성 목록 <br>
│ ├── summary_*.conf # 응급/비응급 판단 설정 파일 <br>
│ ├── Dockerfile # FastAPI 컨테이너화 설정 <br>
├── mini7_06_spring/ # Spring Boot 기반 웹 서비스 (Java) <br>
│ ├── src/ <br>
│ │ ├── main/ <br>
│ │ │ ├── java/com/ai4le/mini7/ <br>
│ │ │ │ ├── client/ # 외부 API 호출 <br>
│ │ │ │ ├── controller/ # API 컨트롤러 <br>
│ │ │ │ ├── dto/ # 데이터 전송 객체 <br>
│ │ │ │ ├── model/ # 도메인 모델 <br>
│ │ │ │ ├── repository/ # 데이터 액세스 계층 <br>
│ │ │ │ ├── service/ # 비즈니스 로직 <br>
│ │ │ │ ├── Mini7Application.java # Spring Boot 메인 실행 파일 <br>
│ │ ├── resources/ <br>
│ │ │ ├── database/ # SQLite 데이터베이스 관련 파일 <br>
│ │ │ ├── static/ # 정적 파일 <br>
│ │ │ ├── templates/ # HTML 템플릿 <br>
│ │ │ ├── application.properties # Spring Boot 설정 파일 <br>
│ │ ├── test/java/com/ai4le/mini7/ # 테스트 코드 <br>
│ ├── Dockerfile # Spring Boot 컨테이너화 설정 <br>
│ ├── build.gradle # Gradle 빌드 스크립트 <br>
│ ├── settings.gradle # Gradle 설정 <br>
├── README.md # 프로젝트 개요 및 문서<br>


---

## 🔍 요구사항 및 구현 범위

### 🎯 **요구사항**
✅ **응급 요청에 적합한 응급실 연계 서비스 구축**  
✅ **음성 데이터 + 위치 정보 활용하여 병원 추천**  
✅ **AI 모델을 통한 응급 여부 판단 및 최적 응급실 매칭**  

### 🏗 **현재까지 적용된 기술**
- **AI 모델**: LLM, 언어모델 파인튜닝
- **API**: OpenAI, Naver Maps
- **H/W**: 클라우드 웹 서버
- **DB**: JPA, SQLite (향후 다양한 DBMS 연동 가능)
- **S/W**: Spring Boot, FastAPI

### 📌 **구현 범위**
- **기본 요구사항** → 전체적인 기능 구현
- **추가 요구사항** → 시간 내 가능하면 확장  
  (ex. DB 확장, 추가적인 병원 추천 알고리즘, GUI 개선 등)

---

## 🚀 프로젝트 실행 방법


```bash
1️⃣ FastAPI 서버 실행 (AI 분석)
cd mini7_06_fastapi
pip install -r requirements.txt
uvicorn main:app --reload

2️⃣ Spring Boot 서버 실행 (웹 서비스)
cd mini7_06_spring
./gradlew bootRun

3️⃣ Docker 컨테이너 실행
docker-compose up --build
```

### 🤝 기여 방법
이 레포지토리를 포크(Fork) 합니다.
새로운 브랜치를 생성합니다. (feature/새로운기능)
변경 사항을 커밋합니다. (git commit -m "추가된 기능 설명")
원격 저장소에 푸시합니다. (git push origin feature/새로운기능)
PR(Pull Request)을 생성하여 변경 사항을 공유합니다.
✨ 더 나은 응급 대응 시스템을 위한 기여를 환영합니다! 🚀


**💡 이렇게 하면 첨부한 "시스템 구성도"와 "요구사항"을 자연스럽게 반영하면서, 전체적인 가독성을 높였습니다.**  
필요한 추가 수정이 있으면 알려주세요! 😊🚀









