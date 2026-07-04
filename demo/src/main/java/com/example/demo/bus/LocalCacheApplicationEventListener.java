package com.example.demo.bus;

import com.example.demo.bean.LocalCache;
import com.example.demo.bus.event.LocalCacheSyncEvent;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class LocalCacheApplicationEventListener implements ApplicationListener<LocalCacheSyncEvent> {

    @Autowired
    private Cache cache;

    @PostConstruct
    public void init() {
        log.info("LocalCacheApplicationEventListener init ...");
    }

    @Override
    public void onApplicationEvent(LocalCacheSyncEvent event) {
        log.info("LocalCacheSyncEvent consume success, local cache: {}", event.getLocalCache());
        LocalCache localCache = event.getLocalCache();
        if (ObjectUtils.isEmpty(localCache.getValue())) {
            cache.invalidate(localCache.getKey());
            return;
        }

        cache.put(localCache.getKey(), localCache.getValue());
        return;
    }
}
