package com.weathermate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherApiResponse {
    private Location location;
    private Current current;
    
    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
    }
    
    @Data
    public static class Current {
        private double temp_c;
        private double temp_f;
        private Condition condition;
        private double wind_kph;
        private double wind_mph;
        private double wind_degree;
        private double pressure_mb;
        private double pressure_in;
        private double precip_mm;
        private double precip_in;
        private int humidity;
        private int cloud;
        private double feelslike_c;
        private double feelslike_f;
        private double vis_km;
        private double vis_miles;
        private double uv;
        private double gust_kph;
        private double gust_mph;
        
        @Data
        public static class Condition {
            private String text;
            private String icon;
            private int code;
        }
    }
}
