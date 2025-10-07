package com.weathermate.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openweather.api")
@Getter
@Setter
public class OpenWeatherConfig {
    private String key;
    private String baseUrl;
    private Integer cacheDurationMinutes;
}


