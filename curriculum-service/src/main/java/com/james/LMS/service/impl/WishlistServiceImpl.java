package com.james.LMS.service.impl;

import com.james.LMS.dto.WishlistDTO;
import com.james.LMS.entity.Wishlist;
import com.james.LMS.repository.WishlistRepository;
import com.james.LMS.service.WishlistService;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
  private final WishlistRepository wishlistRepository;

  @Override
  public void save(Wishlist wishlist) {
    this.wishlistRepository.save(wishlist);
  }

  @Override
  public Optional<Wishlist> findById(Long id) {
    return this.wishlistRepository.findById(id);
  }

  @Override
  public boolean isExistByCurriculumId(Long id) {
    return this.wishlistRepository.existsWishlistByCurriculum_IdAndIsActiveIsTrue(id);
  }

  @Override
  public boolean isExistByCurriculumIdAndUserId(Long id, Long userId) {
    return this.wishlistRepository.existsWishlistByCurriculum_IdAndIsActiveIsTrueAndUserId(
        id, userId);
  }

  @Override
  public Page<WishlistDTO> findAllByUserId(Long userId, Pageable pageable) {
    return this.wishlistRepository.findByUserId(userId, pageable);
  }

  @Override
  public CompletableFuture<Boolean> isExistCurriculumFutureByCurriculumIdAndUserId(
      Long curriculumId, Long userId) {
    return CompletableFuture.supplyAsync(
        () -> this.isExistByCurriculumIdAndUserId(curriculumId, userId));
  }
}
