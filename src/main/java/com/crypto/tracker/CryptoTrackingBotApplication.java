package com.crypto.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.crypto.tracker.config")
public class CryptoTrackingBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoTrackingBotApplication.class, args);
    }
}
