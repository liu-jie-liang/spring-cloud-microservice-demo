package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "timezone.enable", havingValue = "true")
@ConfigurationProperties(prefix = "timezone")
@Data
public class TimeZoneConfig {

    private Boolean enable;

    private Map<String, String> timezones;
}
