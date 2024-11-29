package com.example.redis_spring.services;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheObject(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
        System.out.println("Cached key: " + key + ", value: " + value);
    }

    public Object getCachedObject(String key) {
        Object cachedValue = redisTemplate.opsForValue().get(key);
        System.out.println("Retrieved key: " + key + ", value: " + cachedValue);
        return cachedValue;    }

    public void deleteCachedObject(String key) {
        redisTemplate.delete(key);
    }
}