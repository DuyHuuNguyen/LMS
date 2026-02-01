package com.james.LMS.service.impl;

import com.james.LMS.repository.CartRepository;
import com.james.LMS.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
}
