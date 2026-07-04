package com.example.demo.controller;

import com.example.demo.bus.LocalCacheConsumer;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CacheController {

    @Autowired
    private LocalCacheConsumer localCacheConsumer;

    @Autowired
    private Cache cache;

    @GetMapping("/syncLocalCache")
    public void syncLocalCache(String key, String value) {
        localCacheConsumer.publish(key, value);
    }

    @GetMapping("/getLocalCache")
    public Object getLocalCache(String key) {
        return cache.getIfPresent(key);
    }
}
