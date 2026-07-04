package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;

/**
 * @author
 */
@Slf4j
//@Component
public class ServletInitializer extends SpringBootServletInitializer {

    @PostConstruct
    public void init() {
        log.info("ServletInitializer init ...");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
}
