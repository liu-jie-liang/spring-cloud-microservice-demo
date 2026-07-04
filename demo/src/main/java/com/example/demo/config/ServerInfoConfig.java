package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@RefreshScope
@Configuration
@Data
public class ServerInfoConfig implements Serializable {

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.info}")
    private String serverInfo;

    @Value("${spring.application.name}")
    private String appName;
}
