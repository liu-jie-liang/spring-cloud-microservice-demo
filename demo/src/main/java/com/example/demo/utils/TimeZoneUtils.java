package com.example.demo.utils;

import com.example.demo.config.TimeZoneConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@ConditionalOnBean(TimeZoneConfig.class)
public class TimeZoneUtils {

    @PostConstruct
    public void init() {
        log.info("TimeZoneUtils init ...");
    }

    @Autowired
    private TimeZoneConfig config;

    public Boolean hasSite(String site) {
        return config.getTimezones().containsKey(site);
    }

    public String getTimezoneBySite(String site) {
        if (!hasSite(site)) {
            return null;
        }
        return config.getTimezones().get(site);
    }
}
