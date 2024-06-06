package com.bnk.notificationsenderservice.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StatsServiceImpl {
    RedisTemplate<String, String> redisTemplate;
    RedisLockRegistry redisLockRegistry;

    public String getStatByTaskId(Long taskId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(taskId.toString())).orElse("0");
    }

    public String incrementCurrentStatByTaskId(Long taskId) {

        Lock lock = redisLockRegistry.obtain(taskId.toString());
        try {
            lock.lock();
            String currentTotalString = getStatByTaskId(taskId);
            Long newTotal = Long.valueOf(currentTotalString) + 1;
            log.info("newTotal: {} for TaskId: {}", newTotal, taskId);
            redisTemplate.opsForValue().set(taskId.toString(), newTotal.toString());
            return newTotal.toString();
        } finally {
            lock.unlock();
        }
    }
}
