package com.bus_station_ticket.project.ProjectConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
public class WebConfig implements CorsConfigurationSource {

       @Override
       @NonNull
       public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
              CorsConfiguration config = new CorsConfiguration();

              // Cấu hình allowed origins
              // config.setAllowedOrigins(Arrays.asList(
              // "http://localhost:5173", // Frontend React
              // "http://localhost:3000",
              // "http://localhost:8080" // Backend Spring Boot (nếu cần)
              // ));

              config.addAllowedOriginPattern("*"); // Nếu dùng setAllowCredentials(true)

              // Cấu hình allowed methods
              config.setAllowedMethods(Arrays.asList(
                            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

              // Cấu hình allowed headers
              config.setAllowedHeaders(Arrays.asList("*"));

              // Cho phép gửi cookie và credentials
              config.setAllowCredentials(true);

              // Cấu hình exposed headers
              config.setExposedHeaders(Arrays.asList(
                            "Access-Control-Allow-Origin",
                            "Access-Control-Allow-Credentials",
                            "Authorization"));

              // Cache preflight requests (1 giờ)
              config.setMaxAge(3600L);

              return config;
       }
}