# WeatherMate 🌤️

**날씨 기반 옷/음식/활동 추천 서비스**

현재 날씨에 따라 입을 옷, 먹을 음식, 할 활동을 똑똑하게 추천해주는 Spring Boot 애플리케이션입니다.

## 💡 핵심 기능

### 1️⃣ 날씨 데이터 수집
- OpenWeather API를 통한 실시간 날씨 정보 수집
- 기온, 날씨 상태(맑음, 비, 눈 등), 습도, 풍속 제공
- 도시명 검색 기능

### 2️⃣ 스마트 추천 시스템
날씨 조건에 따른 맞춤 추천:

| 날씨 | 온도 | 옷 추천 | 음식 추천 | 활동 추천 |
|------|------|---------|-----------|-----------|
| ☀️ 맑음 | 25도↑ | 반팔, 선글라스 | 냉면, 빙수 | 산책, 야외활동 |
| ☔ 비 | - | 우산, 긴팔 | 파전, 막걸리 | 실내 영화 |
| 🥶 추움 | <10도 | 코트, 패딩 | 국밥, 찌개 | 카페, 독서 |
| ☁️ 흐림 | - | 가디건 | 커피, 파스타 | 쇼핑 |

### 3️⃣ 캐싱 시스템
- 10분 단위 캐싱으로 API 호출 최소화
- 빠른 응답 속도 및 비용 절감

## 🛠 기술 스택

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Database**: H2 (In-Memory)
- **Cache**: Spring Cache (추후 Redis 확장 가능)
- **External API**: OpenWeather API
- **Build Tool**: Gradle

## 📁 프로젝트 구조

```
weatherMate/
├── src/main/java/com/weathermate/
│   ├── WeatherMateApplication.java
│   ├── config/
│   │   ├── CacheConfig.java
│   │   ├── OpenWeatherConfig.java
│   │   └── WebClientConfig.java
│   ├── controller/
│   │   ├── HealthController.java
│   │   └── WeatherController.java
│   ├── domain/
│   │   ├── Recommendation.java
│   │   └── Weather.java
│   ├── dto/
│   │   ├── OpenWeatherResponse.java
│   │   ├── RecommendationDto.java
│   │   └── WeatherResponse.java
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   ├── repository/
│   │   ├── RecommendationRepository.java
│   │   └── WeatherRepository.java
│   └── service/
│       ├── RecommendationService.java
│       └── WeatherService.java
├── src/main/resources/
│   ├── application.yml
│   └── data.sql (추천 데이터 초기화)
└── src/test/
```

## 🚀 시작하기

### 1. OpenWeather API 키 발급

1. [OpenWeather 회원가입](https://openweathermap.org/api)
2. 무료 API 키 발급
3. `.env` 파일 생성 (`.env.example` 참고)

```bash
OPENWEATHER_API_KEY=your_actual_api_key
```

또는 `application.yml`에서 직접 설정:

```yaml
openweather:
  api:
    key: your_actual_api_key
```

### 2. 프로젝트 실행

```bash
# Windows
gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

### 3. 접속 확인

- **애플리케이션**: http://localhost:8080
- **Health Check**: http://localhost:8080/api/health
- **H2 Console**: http://localhost:8080/h2-console

## 📡 API 엔드포인트

### 1. Health Check
```http
GET /api/health
```

**응답 예시:**
```json
{
  "status": "UP",
  "application": "WeatherMate",
  "timestamp": "2025-10-07T12:00:00"
}
```

### 2. 서울 날씨 조회 (기본)
```http
GET /api/weather
```

### 3. 특정 도시 날씨 조회
```http
GET /api/weather/{city}
```

**예시:**
```http
GET /api/weather/Seoul
GET /api/weather/Busan
GET /api/weather/Incheon
```

**응답 예시:**
```json
{
  "city": "Seoul",
  "temperature": 9.5,
  "weatherCondition": "Rain",
  "description": "가벼운 비",
  "humidity": 75,
  "windSpeed": 3.2,
  "iconCode": "10d",
  "fetchedAt": "2025-10-07T12:00:00",
  "recommendation": {
    "clothing": "우산, 코트, 방수 외투, 따뜻한 옷",
    "food": "라면, 김치찌개, 뜨거운 국물, 호빵",
    "activity": "실내 활동, 영화, 찜질방",
    "emoji": "☔🥶",
    "message": "오늘은 비가 오는 날이에요 ☔ 현재 기온 9.5도! 우산 꼭 챙기세요!"
  }
}
```

## 🗄️ 데이터베이스

### H2 Console 접속
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:weathermate`
- **Username**: `sa`
- **Password**: (비어있음)

### 테이블 구조

**Weather 테이블**
- 날씨 정보 저장 및 캐싱

**Recommendations 테이블**
- 날씨 조건별 추천 데이터
- 12가지 기본 시나리오 제공

## 🔧 설정

### 캐시 설정
`application.yml`에서 캐시 유효 시간 조정:

```yaml
openweather:
  api:
    cache-duration-minutes: 10  # 기본 10분
```

### 로깅 레벨 조정
```yaml
logging:
  level:
    com.weathermate: DEBUG  # 개발: DEBUG, 운영: INFO
```

## 🌟 확장 아이디어

### 즉시 구현 가능
- ✅ 기본 날씨 조회 및 추천
- ✅ 캐싱 시스템
- ✅ 12가지 날씨 시나리오

### 추후 확장 가능
- 🔔 **알림 서비스**: 특정 기온 이하일 때 푸시 알림
- 🗓️ **주간 예보**: 7일치 날씨 + 추천
- 🗺️ **지도형 날씨**: 전국 날씨 지도 뷰
- 👕 **AI 이미지**: Stable Diffusion으로 코디 이미지 생성
- 📱 **반응형 UI**: React/Vue로 프론트엔드 구현
- 💾 **Redis**: 분산 캐싱 시스템
- 🔐 **사용자 인증**: 개인화된 추천

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch
3. Commit your Changes
4. Push to the Branch
5. Open a Pull Request

## 📝 라이선스

이 프로젝트는 포트폴리오용으로 제작되었습니다.

## 📧 문의

프로젝트에 대한 질문이나 제안사항이 있으시면 Issues를 통해 남겨주세요!

