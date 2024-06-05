package com.bnk.notificationsenderservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

@Slf4j
@SpringBootTest
class NotificationSenderServiceApplicationTests {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Test
    void contextLoads() {
        String taskId = "1";
        String total = Optional.ofNullable(redisTemplate.opsForValue().get(taskId)).orElse("0");
        Long currentTotal = Long.valueOf(total);
        log.info("currentTotal: {}", currentTotal);
        redisTemplate.opsForValue().set(taskId, "100");
        currentTotal = Long.valueOf(redisTemplate.opsForValue().get(taskId));
        log.info("NEW currentTotal: {}", currentTotal);
    }

}
