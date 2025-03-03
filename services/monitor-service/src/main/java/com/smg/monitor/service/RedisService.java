package com.smg.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void cacheLatestData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getLatestData(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}