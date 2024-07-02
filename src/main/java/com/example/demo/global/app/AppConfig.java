package com.example.demo.global.app;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Getter
    public static String jwtSecretKey;

    @Value("${custom.jwt.secretKey}")
    public void setJwtSecretKey(String jwtSecretKey) {
        AppConfig.jwtSecretKey = jwtSecretKey;
    }
}
