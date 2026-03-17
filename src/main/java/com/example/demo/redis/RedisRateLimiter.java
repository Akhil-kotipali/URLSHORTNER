package com.example.demo.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;



@Component
public class RedisRateLimiter {

    private final RedisTemplate<String, Integer> redisTemplate;
    @Value("${MAX_REQUESTS}")
    private int MAX_REQUESTS;

    @Value("${TIME_WINDOW}")
    private long TIME_WINDOW;

    
    public RedisRateLimiter(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String clientId) {
    String key = "rate_limit:" + clientId;

    
    Long count = redisTemplate.opsForValue().increment(key);

    
    if (count != null && count == 1) {
        
        redisTemplate.expire(key, Duration.ofSeconds(TIME_WINDOW));
    }

    return count != null && count <= MAX_REQUESTS;
}

}
