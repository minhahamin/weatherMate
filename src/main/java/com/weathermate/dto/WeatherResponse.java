package com.weathermate.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherResponse {
    private String city;
    private Double temperature;
    private String weatherCondition;
    private String description;
    private Integer humidity;
    private Double windSpeed;
    private String iconCode;
    private LocalDateTime fetchedAt;
    private RecommendationDto recommendation;
}


