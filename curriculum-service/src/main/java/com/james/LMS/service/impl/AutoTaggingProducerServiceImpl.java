package com.james.LMS.service.impl;

import com.james.LMS.service.AutoTaggingProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoTaggingProducerServiceImpl implements AutoTaggingProducerService {
    @Override
    public void send(String unSupportedOperation) {
        log.info("hehe chua code {}",unSupportedOperation);
    }
}
