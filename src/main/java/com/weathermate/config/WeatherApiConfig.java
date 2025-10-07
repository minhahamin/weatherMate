package com.weathermate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiConfig {
    private String key;
    private String baseUrl;
    private int cacheDurationMinutes;
}
