package com.example.demo.config;

import com.example.demo.bus.event.LocalCacheSyncEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RemoteApplicationEventScan(basePackageClasses = {LocalCacheSyncEvent.class})
public class BusConfig {

    @PostConstruct
    public void init() {
        log.info("BusConfig init ...");
    }
}
