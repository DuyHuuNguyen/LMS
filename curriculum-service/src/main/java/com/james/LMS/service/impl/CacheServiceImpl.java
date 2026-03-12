package com.james.LMS.service.impl;

import com.james.LMS.dto.InstructorDTO;
import com.james.LMS.service.CacheService;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
  private final RedisTemplate<String, Object> redisTemplate;

  private final RedisTemplate<String, InstructorDTO> instructorDTORedisTemplate;

  @Override
  public void store(String key, Object value, Integer timeOut, TimeUnit timeUnit) {
    this.redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
  }

  @Override
  public void store(String key, Object value) {
    this.redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public Object retrieve(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void delete(String key) {
    this.redisTemplate.delete(key);
  }

  @Override
  public Boolean hasKey(String key) {
    return this.redisTemplate.hasKey(key);
  }

  @Override
  public InstructorDTO retrieveInstructorDTOAndRenewTTL(String key) {
    Object object = instructorDTORedisTemplate.opsForValue().get(key);
    if (object != null) {
      instructorDTORedisTemplate.expire(key, Duration.ofMinutes(10));
    }

    return (InstructorDTO) object;
  }
}
