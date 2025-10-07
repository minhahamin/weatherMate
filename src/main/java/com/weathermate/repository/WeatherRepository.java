package com.weathermate.repository;

import com.weathermate.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    
    Optional<Weather> findFirstByCityOrderByFetchedAtDesc(String city);
    
    Optional<Weather> findFirstByCityAndFetchedAtAfterOrderByFetchedAtDesc(
        String city, 
        LocalDateTime threshold
    );
}


