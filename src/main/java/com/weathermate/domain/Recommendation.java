package com.weathermate.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String weatherCondition; // Clear, Rain, Clouds, Snow 등
    
    @Column(nullable = false)
    private Double minTemp;
    
    @Column(nullable = false)
    private Double maxTemp;
    
    @Column(nullable = false, length = 500)
    private String clothing; // 옷 추천
    
    @Column(nullable = false, length = 500)
    private String food; // 음식 추천
    
    @Column(nullable = false, length = 500)
    private String activity; // 활동 추천
    
    @Column(length = 100)
    private String emoji; // 이모지
}


