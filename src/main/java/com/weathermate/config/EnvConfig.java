package com.weathermate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource(value = "file:.env", ignoreResourceNotFound = true),
    @PropertySource(value = "classpath:.env", ignoreResourceNotFound = true)
})
public class EnvConfig {
    // .env 파일을 자동으로 로드하는 설정
}
