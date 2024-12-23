package com.synchrony.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void invalidateCache(String key) {
        redisTemplate.delete(key);
    }
}
