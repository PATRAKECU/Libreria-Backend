package com.ecommerce.libreriasaintpatrick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // puerto del frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true); // 🔑 esto permite enviar cookies como JSESSIONID
    }
}
