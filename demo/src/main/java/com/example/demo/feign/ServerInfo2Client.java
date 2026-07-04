package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "gateway", contextId = "api-gateway", path = "/service-provider", fallback = Fallback.class)
public interface ServerInfo2Client {

    @RequestMapping(method = RequestMethod.GET, value = "/web/getPort")
    String getPort();
}

@Component
class Fallback implements ServerInfo2Client {

    @Override
    public String getPort() {
        return "circuit breaker feign";
    }
}
