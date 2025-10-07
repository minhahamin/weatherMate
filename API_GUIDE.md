# WeatherMate API 가이드 📚

## 🔑 OpenWeather API 키 발급 방법

### 1단계: 회원가입
1. [OpenWeather 홈페이지](https://openweathermap.org/) 접속
2. 우측 상단 `Sign In` 클릭
3. `Create an Account` 클릭하여 회원가입

### 2단계: API 키 발급
1. 로그인 후 우측 상단 사용자명 클릭
2. `My API Keys` 선택
3. 기본 API 키가 자동으로 생성되어 있음
4. 필요시 `Create key` 버튼으로 추가 키 생성 가능

### 3단계: API 키 활성화
- 새로 발급받은 API 키는 **활성화까지 약 10분~2시간** 소요
- 키가 활성화되기 전에는 `401 Unauthorized` 에러 발생

### 4단계: 프로젝트에 적용

#### 방법 1: application.yml 직접 수정
```yaml
openweather:
  api:
    key: 발급받은_API_키
```

#### 방법 2: 환경 변수 사용 (권장)
Windows PowerShell:
```powershell
$env:OPENWEATHER_API_KEY="발급받은_API_키"
```

Windows CMD:
```cmd
set OPENWEATHER_API_KEY=발급받은_API_키
```

Linux/Mac:
```bash
export OPENWEATHER_API_KEY=발급받은_API_키
```

## 📡 API 사용 예시

### 1. Health Check (API 키 불필요)
```bash
curl http://localhost:8080/api/health
```

### 2. 서울 날씨 조회
```bash
curl http://localhost:8080/api/weather
```

또는

```bash
curl http://localhost:8080/api/weather/Seoul
```

### 3. 다른 도시 날씨 조회
```bash
curl http://localhost:8080/api/weather/Busan
curl http://localhost:8080/api/weather/Incheon
curl http://localhost:8080/api/weather/Tokyo
curl http://localhost:8080/api/weather/NewYork
```

## 🌐 브라우저에서 테스트
- http://localhost:8080/api/health
- http://localhost:8080/api/weather
- http://localhost:8080/api/weather/Seoul
- http://localhost:8080/api/weather/Busan

## 📝 응답 예시

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

## 🔍 지원 도시 목록

### 한국 주요 도시
- Seoul (서울)
- Busan (부산)
- Incheon (인천)
- Daegu (대구)
- Daejeon (대전)
- Gwangju (광주)
- Ulsan (울산)
- Jeju (제주)

### 해외 주요 도시
- Tokyo
- NewYork (띄어쓰기 없이)
- London
- Paris
- Sydney

## ⚠️ 주의사항

### API 사용 제한 (무료 플랜)
- **호출 횟수**: 60 calls/minute, 1,000,000 calls/month
- **응답 시간**: ~1초 이내
- **데이터 업데이트**: 10분마다

### 캐싱 정책
- WeatherMate는 **10분 단위 캐싱** 적용
- 같은 도시를 10분 내 재조회 시 DB 캐시 데이터 반환
- API 호출 횟수 절감 및 빠른 응답

## 🐛 트러블슈팅

### 401 Unauthorized 에러
- API 키가 아직 활성화되지 않음 (최대 2시간 대기)
- API 키가 잘못 입력됨

### 404 Not Found 에러
- 잘못된 도시명 입력
- 영문 도시명으로 재시도

### 500 Internal Server Error
- API 키가 설정되지 않음
- `application.yml` 또는 환경 변수 확인

## 💡 팁

1. **로컬 테스트 시**: `application.yml`에 직접 입력이 편리
2. **배포 시**: 환경 변수 사용 권장 (보안)
3. **캐싱 확인**: 같은 도시를 연속으로 조회하면 로그에 "캐시된 날씨 데이터 사용" 메시지 출력


