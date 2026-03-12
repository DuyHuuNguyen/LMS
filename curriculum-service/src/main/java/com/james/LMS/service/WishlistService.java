package com.james.LMS.service;

import com.james.LMS.entity.Wishlist;
import java.util.Optional;

public interface WishlistService {
  void save(Wishlist wishlist);

  Optional<Wishlist> findById(Long id);

  boolean isExistByCurriculumId(Long id);

  boolean isExistByCurriculumIdAndUserId(Long id, Long userId);
}
