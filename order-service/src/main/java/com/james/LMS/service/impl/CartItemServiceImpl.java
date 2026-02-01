package com.james.LMS.service.impl;

import com.james.LMS.repository.CartItemRepository;
import com.james.LMS.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
  private final CartItemRepository cartItemRepository;
}
