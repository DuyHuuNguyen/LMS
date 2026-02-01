package com.james.LMS.facade.impl;

import com.james.LMS.facade.CartFacade;
import com.james.LMS.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartFacadeImpl implements CartFacade {
  private final CartService cartService;
}
