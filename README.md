# WeatherMate

날씨 정보를 제공하는 Spring Boot 애플리케이션입니다.

## 기술 스택

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Database**: H2 (In-Memory)
- **Build Tool**: Gradle

## 프로젝트 구조

```
weatherMate/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── weathermate/
│   │   │           ├── WeatherMateApplication.java
│   │   │           └── controller/
│   │   │               └── HealthController.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/
│               └── weathermate/
│                   └── WeatherMateApplicationTests.java
├── build.gradle
├── settings.gradle
└── README.md
```

## 실행 방법

### Gradle을 이용한 실행

```bash
# Windows
gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

### 빌드

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

### 실행 후 접속 주소

- **애플리케이션**: http://localhost:8080
- **Health Check**: http://localhost:8080/api/health
- **H2 Console**: http://localhost:8080/h2-console

## H2 데이터베이스 접속 정보

- **JDBC URL**: jdbc:h2:mem:weathermate
- **Username**: sa
- **Password**: (비어있음)

## API 엔드포인트

### Health Check
```
GET /api/health
```

응답 예시:
```json
{
  "status": "UP",
  "application": "WeatherMate",
  "timestamp": "2025-10-07T..."
}
```

## 개발 환경

- Spring Boot DevTools가 포함되어 있어 코드 변경 시 자동으로 재시작됩니다.
- Lombok이 설정되어 있어 보일러플레이트 코드를 줄일 수 있습니다.

## 다음 단계

1. 날씨 API 연동 (OpenWeatherMap, 기상청 API 등)
2. 위치 기반 서비스 구현
3. 데이터베이스 스키마 설계
4. REST API 개발
5. 프론트엔드 연동

