package com.example.redis_spring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public <T> void cacheObject(String key, T value, long timeout, TimeUnit unit) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, timeout, unit);
        } catch (Exception e) {
            log.error("Error caching key: {}, error: {}", key, e.getMessage());
        }
    }

    public <T> T getCachedObject(String key, TypeReference<T> typeReference) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                return objectMapper.readValue(json, typeReference);
            }
        } catch (Exception e) {
            log.error("Error retrieving key: {}, error: {}", key, e.getMessage());
        }
        return null;
    }
    public void deleteCachedObject(String key) {
        try {
            redisTemplate.delete(key);
            log.info("Deleted key: {}", key);
        } catch (Exception e) {
            log.error("Error deleting key: {}, error: {}", key, e.getMessage());
        }
    }

}
