package com.example.demo.bus;

import com.example.demo.bus.event.LocalCacheSyncEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.PathDestinationFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class LocalCacheConsumer {

    @PostConstruct
    public void init() {
        log.info("LocalCacheConsumer init ...");
    }

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private BusProperties busProperties;

    @Autowired
    private PathDestinationFactory pathDestinationFactory;

    public void publish(String key, Object value) {
        log.info("busProperties: {}", busProperties);
        LocalCacheSyncEvent event = new LocalCacheSyncEvent(this, busProperties.getId(),
                pathDestinationFactory.getDestination(null), key, value);
        publisher.publishEvent(event);
    }
}
