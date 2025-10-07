package com.weathermate.service;

import com.weathermate.config.OpenWeatherConfig;
import com.weathermate.domain.Weather;
import com.weathermate.dto.OpenWeatherResponse;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WeatherService {
    
    private final WeatherRepository weatherRepository;
    private final OpenWeatherConfig openWeatherConfig;
    private final WebClient webClient;
    private final RecommendationService recommendationService;
    private final MockWeatherService mockWeatherService;
    
    @Cacheable(value = "weather-data", key = "#city")
    public WeatherResponse getWeatherByCity(String city) {
        log.info("날씨 정보 조회 시작: {}", city);
        
        // 캐시 유효 시간 계산 (10분)
        LocalDateTime cacheThreshold = LocalDateTime.now()
            .minusMinutes(openWeatherConfig.getCacheDurationMinutes());
        
        // DB에서 최근 데이터 조회
        Optional<Weather> cachedWeather = weatherRepository
            .findFirstByCityAndFetchedAtAfterOrderByFetchedAtDesc(city, cacheThreshold);
        
        if (cachedWeather.isPresent()) {
            log.info("캐시된 날씨 데이터 사용: {}", city);
            return buildWeatherResponse(cachedWeather.get());
        }
        
        try {
            // API 호출 시도
            log.info("OpenWeather API 호출: {}", city);
            Weather weather = fetchWeatherFromAPI(city);
            
            // DB에 저장
            weatherRepository.save(weather);
            
            return buildWeatherResponse(weather);
        } catch (Exception e) {
            log.warn("API 호출 실패, Mock 데이터 사용: {}", e.getMessage());
            return mockWeatherService.getMockWeatherByCity(city);
        }
    }
    
    private Weather fetchWeatherFromAPI(String city) {
        String url = String.format("%s/weather?q=%s&appid=%s&units=metric&lang=kr",
            openWeatherConfig.getBaseUrl(),
            city,
            openWeatherConfig.getKey()
        );
        
        OpenWeatherResponse response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(OpenWeatherResponse.class)
            .onErrorResume(e -> {
                log.error("OpenWeather API 호출 실패: {}", e.getMessage());
                return Mono.empty();
            })
            .block();
        
        if (response == null || response.getWeather().isEmpty()) {
            throw new RuntimeException("날씨 데이터를 가져올 수 없습니다.");
        }
        
        return Weather.builder()
            .city(response.getName())
            .temperature(response.getMain().getTemp())
            .weatherCondition(response.getWeather().get(0).getMain())
            .description(response.getWeather().get(0).getDescription())
            .humidity(response.getMain().getHumidity())
            .windSpeed(response.getWind().getSpeed())
            .iconCode(response.getWeather().get(0).getIcon())
            .fetchedAt(LocalDateTime.now())
            .build();
    }
    
    private WeatherResponse buildWeatherResponse(Weather weather) {
        return WeatherResponse.builder()
            .city(weather.getCity())
            .temperature(weather.getTemperature())
            .weatherCondition(weather.getWeatherCondition())
            .description(weather.getDescription())
            .humidity(weather.getHumidity())
            .windSpeed(weather.getWindSpeed())
            .iconCode(weather.getIconCode())
            .fetchedAt(weather.getFetchedAt())
            .recommendation(recommendationService.getRecommendation(
                weather.getWeatherCondition(), 
                weather.getTemperature()
            ))
            .build();
    }
}


