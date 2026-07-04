package com.example.demo.bus.event;

import com.example.demo.bean.LocalCache;
import lombok.Getter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class LocalCacheSyncEvent extends RemoteApplicationEvent {

    @Getter
    private LocalCache localCache;

    public LocalCacheSyncEvent() {
        super();
    }

    public LocalCacheSyncEvent(Object source, String originService, Destination destination) {
        super(source, originService, destination);
    }

    public LocalCacheSyncEvent(Object source, String originService, Destination destination, String key, Object value) {
        super(source, originService, destination);
        this.localCache = new LocalCache(key, value);
    }
}
