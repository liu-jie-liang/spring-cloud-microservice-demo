package com.example.demo.controller;

import com.example.demo.config.ServerInfoConfig;
import com.example.demo.feign.ServerInfo2Client;
import com.example.demo.feign.ServerInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
class ServiceInstanceRestController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/v2/test_rewrite/post")
    public String testRewriteByPost(@RequestBody String body) {
        log.info("body: {}", body);
        return String.format("test rewrite success, body: %s", body);
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/getInstances")
    public List<ServiceInstance> getInstances() {
        String appName = serverInfoConfig.getAppName();
        log.info("appName: {}", appName);
        return discoveryClient.getInstances(appName);
    }

    @Autowired
    private ServerInfoConfig serverInfoConfig;

    @GetMapping("/getServerInfo")
    public String getServerInfo() {
        return serverInfoConfig.getServerInfo();
    }

    @GetMapping("/getPort")
    public String getPort() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // ignore
        }
        String appName = serverInfoConfig.getAppName();
        log.info("appName: {}", appName);
        if ("service-provider".equalsIgnoreCase(appName)) {
            return serverInfoConfig.getServerPort();
        }
        return restTemplate.getForEntity("http://service-provider/web/getPort", String.class).getBody();
    }

    @Autowired
    private ServerInfoClient serverInfoClient;

    @GetMapping("getPort2")
    public String getPort2() {
        String appName = serverInfoConfig.getAppName();
        log.info("appName: {}", appName);
        if ("service-provider".equalsIgnoreCase(appName)) {
            return serverInfoConfig.getServerPort();
        }
        return serverInfoClient.getPort();
    }

    @Autowired
    private FeignClientProperties feignClientProperties;

    @Autowired
    private ServerInfo2Client serverInfo2Client;

    @GetMapping("getPort3")
    public String getPort3() {
        log.info("feignClientProperties: {}", feignClientProperties);
        String appName = serverInfoConfig.getAppName();
        log.info("appName: {}", appName);
        if ("service-provider".equalsIgnoreCase(appName)) {
            return serverInfoConfig.getServerPort();
        }
        return serverInfo2Client.getPort();
    }

    @GetMapping("fallback")
    public String fallback() {
        return "circuit breaker gateway";
    }
}
