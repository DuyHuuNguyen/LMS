package com.james.LMS.service;

import com.james.LMS.dto.WishlistDTO;
import com.james.LMS.entity.Wishlist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
  void save(Wishlist wishlist);

  Optional<Wishlist> findById(Long id);

  boolean isExistByCurriculumId(Long id);

  boolean isExistByCurriculumIdAndUserId(Long id, Long userId);

  Page<WishlistDTO> findAllByUserId(Long userId, Pageable pageable);
}
