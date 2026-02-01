package com.james.LMS.service.impl;

import com.james.LMS.repository.OrderRepository;
import com.james.LMS.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
}
