package com.weathermate.service;

import com.weathermate.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "weather.mock.enabled", havingValue = "true")
public class MockWeatherService {
    
    private final RecommendationService recommendationService;
    private final Random random = new Random();
    
    public WeatherResponse getMockWeatherByCity(String city) {
        log.info("Mock 날씨 데이터 사용: {}", city);
        
        // 다양한 날씨 시나리오 생성
        String[] weatherConditions = {"Clear", "Clouds", "Rain", "Snow"};
        String[] descriptions = {"Sunny", "Cloudy", "Light Rain", "Snow"};
        
        int index = random.nextInt(weatherConditions.length);
        String weatherCondition = weatherConditions[index];
        String description = descriptions[index];
        
        log.info("Mock data generated: {} - {} ({})", city, description, weatherCondition);
        
        // 온도 범위 (5도 ~ 30도)
        double temperature = 5 + random.nextDouble() * 25;
        
        WeatherResponse response = WeatherResponse.builder()
            .city(city)
            .temperature(Math.round(temperature * 10.0) / 10.0)
            .weatherCondition(weatherCondition)
            .description(description)
            .humidity(50 + random.nextInt(40)) // 50-90%
            .windSpeed(Math.round((1 + random.nextDouble() * 5) * 10.0) / 10.0) // 1-6 m/s
            .iconCode("01d")
            .fetchedAt(LocalDateTime.now())
            .recommendation(recommendationService.getRecommendation(weatherCondition, temperature))
            .build();
            
        log.info("Mock 날씨 데이터 생성 완료: {} - {}도, {}", city, temperature, description);
        return response;
    }
}
