package com.james.LMS.facade.impl;

import com.james.LMS.facade.OrderFacade;
import com.james.LMS.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {
  private final OrderService orderService;
}
