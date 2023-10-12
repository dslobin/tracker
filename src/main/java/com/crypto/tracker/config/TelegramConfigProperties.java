package com.crypto.tracker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "telegram.bot")
@ConfigurationPropertiesScan
@Getter
@Setter
public class TelegramConfigProperties {
    private String token;
    private String name;
    private long userLimit;
}
