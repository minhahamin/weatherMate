package com.weathermate.controller;

import com.weathermate.dto.WeatherResponse;
import com.weathermate.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // 프론트엔드 연동을 위해
public class WeatherController {
    
    private final WeatherService weatherService;
    
    /**
     * 도시별 날씨 조회 및 추천 정보
     * 
     * @param city 도시명 (예: Seoul, Busan, Incheon)
     * @return 날씨 정보 + 옷/음식/활동 추천
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeatherByCity(@PathVariable String city) {
        log.info("날씨 조회 요청: {}", city);
        WeatherResponse response = weatherService.getWeatherByCity(city);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 기본 도시 (서울) 날씨 조회
     */
    @GetMapping
    public ResponseEntity<WeatherResponse> getDefaultWeather() {
        log.info("기본 날씨 조회 요청 (서울)");
        WeatherResponse response = weatherService.getWeatherByCity("Seoul");
        return ResponseEntity.ok(response);
    }
}


