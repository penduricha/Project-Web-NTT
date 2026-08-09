package com.example.be_web_students.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry)  {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:5173", //vue
                        "http://localhost:5174",
                        "http://localhost:3000",  // ReactJS
                        "http://localhost:8081",
                        // React Native may minh
                        // dùng http://localhost:8082
                        "http://localhost:4200",  // Angular
                        "http://localhost:3001" ,//Next.js
                        "http://127.0.0.1:5173/",//
                        "http://127.0.0.1:5174/"

                )
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
