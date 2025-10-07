package com.weathermate.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Spring Cache 기본 설정 활성화
    // 추후 Redis로 전환 가능
}


