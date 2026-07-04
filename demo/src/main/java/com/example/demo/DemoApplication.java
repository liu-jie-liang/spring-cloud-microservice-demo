package com.example.demo;

import com.example.demo.config.TimeZoneConfig;
import com.example.demo.utils.RequestContextUtils;
import com.example.demo.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients
public class DemoApplication {

    @Value("${timezone.default}")
    private String timezone;

    @PostConstruct
    public void init() {
        log.info("DemoApplication init ...");

        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    }

    @RequestMapping("/")
    public String index() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    @GetMapping("testSlf4j")
    public String testSlf4j(String name, String site) {
        log.info("site: {}", site);
        return RequestContextUtils.getParam(name);
    }

    @GetMapping("getProp")
    public String getProp() {
        TimeZoneConfig config = SpringContextUtils.getBean(TimeZoneConfig.class);
        return config.getEnable().toString() + " ddd";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}