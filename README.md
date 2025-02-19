# 🚑 응급상황 인식 및 응급실 연계 서비스 포탈

## 📌 개요
본 프로젝트는 응급상황을 신속하게 인식하고, 적절한 응급실과 연계하는 웹 서비스 포탈입니다.  
자연어 처리(NLP) 및 음성 데이터를 활용하여 응급 여부를 판단하며, 네이버 지도 API를 사용해 가까운 응급실을 추천합니다.

### 🎬 시연 영상
[![시연 영상](https://raw.githubusercontent.com/lyrWinterCat/KT_Aivle_MINI7/main/EmergencyReport.png)](https://www.youtube.com/watch?v=bYSpKPcFpw0)


---

## 🛠 사용 기술

### **📌 개발 도구**
- **IDE**: Visual Studio Code, InteliJ
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
mini7_project/ <br>
├── mini7_06_fastapi/ # FastAPI 기반 AI 분석 서버 (Python) <br>
│ ├── db/ # 데이터베이스 관련 파일 (SQLite) <br>
│ ├── fine_tuned_bert/ # 파인튜닝된 BERT 모델 <br>
│ ├── emergency.py # 응급 상황 판별 로직 <br>
│ ├── main.py # FastAPI 엔트리 포인트 <br>
│ ├── requirements.txt # Python 라이브러리 의존성 목록 <br>
│ ├── summary_*.conf # 응급/비응급 판단 설정 파일 <br>
│ ├── Dockerfile # FastAPI 컨테이너화 설정 <br>
│── mini7_06_spring/ # Spring Boot 기반 웹 서비스 (Java) <br>
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
│ │ │ ├── templates/ # HTML 템플릿 (추후 확장 가능) <br>
│ │ │ ├── application.properties # Spring Boot 설정 파일 <br>
│ │ ├── test/java/com/ai4le/mini7/ # 테스트 코드 <br>
│ ├── Dockerfile # Spring Boot 컨테이너화 설정 <br>
│ ├── build.gradle # Gradle 빌드 스크립트 <br>
│ ├── settings.gradle # Gradle 설정 <br>
│ │── README.md # 프로젝트 개요 및 문서<br>


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


## 🤝 기여 방법
이 레포지토리를 포크(Fork) 합니다. <br>
새로운 브랜치를 생성합니다. (feature/새로운기능) <br>
변경 사항을 커밋합니다. (git commit -m "추가된 기능 설명") <br>
원격 저장소에 푸시합니다. (git push origin feature/새로운기능) <br>
PR(Pull Request)을 생성하여 변경 사항을 공유합니다. <br>
✨ 더 나은 응급 대응 시스템을 위한 기여를 환영합니다! 🚀 <br>

