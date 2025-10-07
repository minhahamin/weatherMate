package com.weathermate.service;

import com.weathermate.config.WeatherApiConfig;
import com.weathermate.domain.Weather;
import com.weathermate.dto.WeatherApiResponse;
import com.weathermate.dto.WeatherResponse;
import com.weathermate.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherApiConfig weatherApiConfig;
    private final WebClient webClient;
    private final RecommendationService recommendationService;

    @Cacheable(value = "weather-data", key = "#city")
    public WeatherResponse getWeatherByCity(String city) {
        log.info("날씨 정보 조회 시작: {}", city);

        // 도시명을 영어로 변환
        String englishCityName = convertToEnglishCityName(city);
        
        try {
            // 실제 WeatherAPI.com 호출
            log.info("WeatherAPI.com 호출: {}", englishCityName);
            Weather weather = fetchWeatherFromAPI(englishCityName);

            // DB에 저장
            weatherRepository.save(weather);

            return buildWeatherResponse(weather);
        } catch (Exception e) {
            log.error("WeatherAPI 호출 실패: {}", e.getMessage());
            throw new RuntimeException("날씨 데이터를 가져올 수 없습니다: " + e.getMessage());
        }
    }
    
    private String convertToEnglishCityName(String city) {
        switch (city.toLowerCase()) {
            case "서울": return "Seoul";
            case "부산": return "Busan";
            case "인천": return "Incheon";
            case "대구": return "Daegu";
            case "광주": return "Gwangju";
            case "대전": return "Daejeon";
            case "울산": return "Ulsan";
            case "제주": return "Jeju";
            default: return city; // 이미 영어인 경우 그대로 반환
        }
    }
    
    private Weather fetchWeatherFromAPI(String city) {
        String url = String.format("%s/current.json?key=%s&q=%s&aqi=no",
            weatherApiConfig.getBaseUrl(),
            weatherApiConfig.getKey(),
            city
        );
        
        log.info("WeatherAPI URL: {}", url);
        
        WeatherApiResponse response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(WeatherApiResponse.class)
            .onErrorResume(e -> {
                log.error("WeatherAPI 호출 실패: {}", e.getMessage());
                return Mono.error(new RuntimeException("WeatherAPI 호출 실패: " + e.getMessage()));
            })
            .block();
        
        if (response == null || response.getCurrent() == null) {
            throw new RuntimeException("날씨 데이터를 가져올 수 없습니다.");
        }
        
        return Weather.builder()
            .city(response.getLocation().getName())
            .temperature(response.getCurrent().getTemp_c())
            .weatherCondition(convertWeatherCondition(response.getCurrent().getCondition().getText()))
            .description(response.getCurrent().getCondition().getText())
            .humidity(response.getCurrent().getHumidity())
            .windSpeed(response.getCurrent().getWind_kph() / 3.6) // km/h를 m/s로 변환
            .iconCode(response.getCurrent().getCondition().getIcon())
            .fetchedAt(LocalDateTime.now())
            .build();
    }
    
    private String convertWeatherCondition(String condition) {
        String lowerCondition = condition.toLowerCase();
        
        if (lowerCondition.contains("sunny") || lowerCondition.contains("clear")) {
            return "Clear";
        } else if (lowerCondition.contains("overcast") || lowerCondition.contains("cloud")) {
            return "Clouds";
        } else if (lowerCondition.contains("rain") || lowerCondition.contains("drizzle")) {
            return "Rain";
        } else if (lowerCondition.contains("snow")) {
            return "Snow";
        } else if (lowerCondition.contains("mist") || lowerCondition.contains("fog")) {
            return "Mist";
        } else if (lowerCondition.contains("thunder")) {
            return "Thunderstorm";
        } else {
            return "Clear"; // 기본값
        }
    }
    
    private WeatherResponse buildWeatherResponse(Weather weather) {
        WeatherResponse response = WeatherResponse.builder()
            .city(weather.getCity())
            .temperature(weather.getTemperature())
            .weatherCondition(weather.getWeatherCondition())
            .description(weather.getDescription())
            .humidity(weather.getHumidity())
            .windSpeed(weather.getWindSpeed())
            .iconCode(weather.getIconCode())
            .fetchedAt(weather.getFetchedAt())
            .build();

        // 추천 정보 추가
        response.setRecommendation(recommendationService.getRecommendation(
            weather.getWeatherCondition(),
            weather.getTemperature()
        ));

        return response;
    }
}