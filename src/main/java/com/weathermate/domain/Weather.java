package com.weathermate.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private Double temperature;
    
    @Column(nullable = false)
    private String weatherCondition; // Clear, Rain, Clouds, Snow 등
    
    private String description; // 상세 설명
    
    private Integer humidity;
    
    private Double windSpeed;
    
    @Column(nullable = false)
    private LocalDateTime fetchedAt;
    
    @Column(name = "icon_code")
    private String iconCode; // OpenWeather 아이콘 코드
}


