package com.weathermate.service;

import com.weathermate.domain.Recommendation;
import com.weathermate.dto.RecommendationDto;
import com.weathermate.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RecommendationService {
    
    private final RecommendationRepository recommendationRepository;
    private static final Pattern EMOJI_PATTERN = Pattern.compile(
        "[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\uD83C\\uDDE0-\\uD83C\\uDDFF]|[\\u2600-\\u26FF]|[\\u2700-\\u27BF]"
    );
    
    public RecommendationDto getRecommendation(String weatherCondition, Double temperature) {
        log.info("추천 정보 조회: {} / {}도", weatherCondition, temperature);
        
        Optional<Recommendation> recommendation = recommendationRepository
            .findByWeatherConditionAndTemperature(weatherCondition, temperature);
        
        if (recommendation.isPresent()) {
            Recommendation rec = recommendation.get();
        return RecommendationDto.builder()
            .clothing(rec.getClothing())
            .food(rec.getFood())
            .activity(rec.getActivity())
            .emoji(cleanEmoji(rec.getEmoji()))
            .message(generateMessage(weatherCondition, temperature, rec))
            .build();
        }
        
        // 기본 추천
        return getDefaultRecommendation(temperature);
    }
    
    private String generateMessage(String condition, Double temp, Recommendation rec) {
        String tempStr = String.format("%.1f", temp);
        
        switch (condition.toLowerCase()) {
            case "rain":
                return String.format("It's a rainy day ☔ Current temperature is %s°C! Don't forget your umbrella!", tempStr);
            case "clear":
                if (temp >= 25) {
                    return String.format("Beautiful sunny weather ☀️ Current temperature is %s°C! Perfect for a cold drink!", tempStr);
                } else if (temp < 10) {
                    return String.format("Clear but chilly weather 🌤️ Current temperature is %s°C! Dress warmly!", tempStr);
                } else {
                    return String.format("Perfect weather ☀️ Current temperature is %s°C! Great for a walk!", tempStr);
                }
            case "clouds":
                return String.format("It's a cloudy day ☁️ Current temperature is %s°C!", tempStr);
            case "snow":
                return String.format("It's snowing ❄️ Current temperature is %s°C! Stay warm!", tempStr);
            default:
                return String.format("Current temperature is %s°C!", tempStr);
        }
    }
    
    private RecommendationDto getDefaultRecommendation(Double temperature) {
        String clothing, food, activity, emoji;
        
        if (temperature >= 28) {
            clothing = "Tank top, Short sleeves, Shorts, Dress";
            food = "Cold noodles, Shaved ice, Ice cream, Salad";
            activity = "Swimming, Water activities, Indoor activities";
            emoji = "🌞";
        } else if (temperature >= 23) {
            clothing = "Short sleeves, Light shirt, Cotton pants";
            food = "Cold drinks, Fruits, Light salad";
            activity = "Walking, Cycling, Outdoor activities";
            emoji = "☀️";
        } else if (temperature >= 20) {
            clothing = "Light cardigan, Long sleeves, Cotton pants";
            food = "Coffee, Sandwich, Pasta";
            activity = "Walking, Cafe, Shopping";
            emoji = "🌤️";
        } else if (temperature >= 17) {
            clothing = "Cardigan, Knit, Jeans";
            food = "Hot coffee, Bread, Soup";
            activity = "Reading, Movie, Cafe";
            emoji = "☁️";
        } else if (temperature >= 12) {
            clothing = "Jacket, Cardigan, Sweater";
            food = "Hot soup, Stew, Tea";
            activity = "Indoor activities, Museum, Cafe";
            emoji = "🧥";
        } else if (temperature >= 9) {
            clothing = "Coat, Thick knit, Scarf";
            food = "Rice soup, Stew, Ramen, Hot drinks";
            activity = "Indoor activities, Movie, Reading";
            emoji = "🧥";
        } else if (temperature >= 5) {
            clothing = "Coat, Heat tech, Scarf, Gloves";
            food = "Rice soup, Hot soup, Hot chocolate, Fish bread";
            activity = "Indoor activities, Sauna";
            emoji = "🧤";
        } else {
            clothing = "Padded jacket, Thick coat, Scarf, Gloves, Hat";
            food = "Hot soup, Hot bun, Hot pack essential!";
            activity = "Stay warm indoors";
            emoji = "🥶";
        }
        
        return RecommendationDto.builder()
            .clothing(clothing)
            .food(food)
            .activity(activity)
            .emoji(cleanEmoji(emoji))
            .message(generateMessage("default", temperature, null))
            .build();
    }
    
    private String cleanEmoji(String emoji) {
        if (emoji == null || emoji.trim().isEmpty()) {
            return "";
        }
        
        // Simple approach: keep only basic emoji characters and remove corrupted ones
        // Use basic Unicode ranges for emojis
        return emoji.replaceAll("[^\\u2600-\\u27BF\\uD83C-\\uD83E\\uD83D-\\uD83D\\u1F600-\\u1F64F\\u1F300-\\u1F5FF\\u1F680-\\u1F6FF]", "");
    }
}


