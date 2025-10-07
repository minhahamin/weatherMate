package com.weathermate.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationDto {
    private String clothing;
    private String food;
    private String activity;
    private String emoji;
    private String message; // 개인화된 메시지
}


