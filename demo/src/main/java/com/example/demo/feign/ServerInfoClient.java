package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-provider", contextId = "provider")
public interface ServerInfoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/web/getPort")
    String getPort();
}
