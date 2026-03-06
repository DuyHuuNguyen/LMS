package com.james.LMS.service.impl;

import com.james.LMS.entity.Wishlist;
import com.james.LMS.repository.WishlistRepository;
import com.james.LMS.service.WishlistService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
}
