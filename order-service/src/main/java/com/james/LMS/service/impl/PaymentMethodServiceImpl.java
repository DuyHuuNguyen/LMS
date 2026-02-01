package com.james.LMS.service.impl;

import com.james.LMS.repository.PaymentMethodRepository;
import com.james.LMS.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;
}
