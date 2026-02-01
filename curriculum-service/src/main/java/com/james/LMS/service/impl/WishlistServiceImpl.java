package com.james.LMS.service.impl;

import com.james.LMS.repository.WishlistRepository;
import com.james.LMS.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
  private final WishlistRepository wishlistRepository;
}
