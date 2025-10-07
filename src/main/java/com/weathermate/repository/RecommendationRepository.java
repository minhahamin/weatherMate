package com.weathermate.repository;

import com.weathermate.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    
    @Query("SELECT r FROM Recommendation r WHERE " +
           "r.weatherCondition = :condition AND " +
           "r.minTemp <= :temp AND r.maxTemp > :temp")
    Optional<Recommendation> findByWeatherConditionAndTemperature(
        @Param("condition") String condition,
        @Param("temp") Double temp
    );
}


