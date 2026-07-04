package com.example.demo.annotation;

import com.example.demo.constant.CacheType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache {

    String prefix();

    String key(); //支持springEl表达式

    long expire() default 3600L;

    CacheType type() default CacheType.FULL;
}


