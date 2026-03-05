package com.james.LMS.service.impl;

import com.james.LMS.dto.BannerDTO;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.service.BannerService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
  private static final String BANNER_KEY = "banners:list-active";
  private static final String BANNER_ID_SEQUENCE_KEY = "banners:id:sequence";

  private final RedisTemplate<String, BannerDTO> redisBannerTemplate;
  private final RedisTemplate<String, Object> redisTemplate;


  @Override
  public void hiddenBannerById(String id) {
    if (id == null || id.isBlank()) {
      throw new EntityNotFoundException(ErrorCode.BANNER_NOT_FOUND);
    }

    ListOperations<String, BannerDTO> listOperations = redisBannerTemplate.opsForList();
    List<BannerDTO> banners = listOperations.range(BANNER_KEY, 0, -1);

    if (banners == null || banners.isEmpty()) {
      throw new EntityNotFoundException(ErrorCode.BANNER_NOT_FOUND);
    }

    BannerDTO targetBanner =
        banners.stream()
            .filter(banner -> Objects.equals(banner.getId(), id))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BANNER_NOT_FOUND));

    Long removedCount = listOperations.remove(BANNER_KEY, 1, targetBanner);
    if (removedCount == null || removedCount == 0) {
      throw new EntityNotFoundException(ErrorCode.BANNER_NOT_FOUND);
    }
  }

  @Override
  public List<BannerDTO> findAll() {
    List<BannerDTO> banners = redisBannerTemplate.opsForList().range(BANNER_KEY, 0, -1).stream().filter(BannerDTO::getIsActive).toList();
    return banners == null ? Collections.emptyList() : banners;
  }


  @Override
  public void storeWithoutTimeout(BannerDTO bannerDTO) {
    if (bannerDTO.getId() == null || bannerDTO.getId().isBlank()) {
      Long sequence = redisTemplate.opsForValue().increment(BANNER_ID_SEQUENCE_KEY);
      if (sequence != null) {
        bannerDTO.setId(sequence.toString());
      }
    }

    ListOperations<String, BannerDTO> listOperations = redisBannerTemplate.opsForList();
    listOperations.rightPush(BANNER_KEY, bannerDTO);
  }
}
