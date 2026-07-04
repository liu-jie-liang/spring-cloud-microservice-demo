package com.example.demo.aop;

import com.example.demo.annotation.DoubleCache;
import com.example.demo.bus.LocalCacheConsumer;
import com.example.demo.constant.CacheType;
import com.example.demo.utils.ElParserUtils;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Aspect
@AllArgsConstructor
public class CacheAspect {

    @Autowired
    private Cache cache;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LocalCacheConsumer localCacheConsumer;

    @PostConstruct
    public void init() {
        log.info("CacheAspect init ...");
    }

    @Pointcut("@annotation(com.example.demo.annotation.DoubleCache)")
    public void cachePointcut() {
    }

    @Around("cachePointcut()")
    public Object cacheAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 拼接解析springEl表达式的map
        String[] paramNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            treeMap.put(paramNames[i], args[i]);
        }

        DoubleCache annotation = method.getAnnotation(DoubleCache.class);
        String elResult = ElParserUtils.parse(annotation.key(), treeMap);
        String realKey = annotation.prefix() + "." + elResult;

        //强制更新
        if (annotation.type() == CacheType.PUT) {
            Object object = point.proceed();
            redisTemplate.opsForValue().set(realKey, object, annotation.expire(), TimeUnit.SECONDS);
//            cache.invalidate(realKey);
            localCacheConsumer.publish(realKey, null);
            return object;
        }

        //删除
        else if (annotation.type() == CacheType.DELETE) {
            Object object = point.proceed();
            // 延迟双删
            redisTemplate.delete(realKey);
//            cache.invalidate(realKey);
            localCacheConsumer.publish(realKey, null);
            Thread.sleep(1000);
            redisTemplate.delete(realKey);
//            cache.invalidate(realKey);
            localCacheConsumer.publish(realKey, null);
            return object;
        }

        //读写，查询Caffeine
        Object caffeineCache = cache.getIfPresent(realKey);
        if (Objects.nonNull(caffeineCache)) {
            log.info("get data from caffeine");
            return caffeineCache;
        }

        //查询Redis
        Object redisCache = redisTemplate.opsForValue().get(realKey);
        if (Objects.nonNull(redisCache)) {
            log.info("get data from redis");
            cache.put(realKey, redisCache);
            return redisCache;
        } else if (redisTemplate.hasKey(realKey)) {
            return redisCache;
        }

        Object object = point.proceed();
        log.info("get data from database");
        //写入Redis
        redisTemplate.opsForValue().set(realKey, object, annotation.expire(), TimeUnit.SECONDS);

        return object;
    }
}

