package com.james.LMS.service;

import com.james.LMS.dto.PayloadCurriculumVectorMessage;

public interface PayloadCurriculumProducerService {
    void sent(PayloadCurriculumVectorMessage payloadCurriculumVectorMessage);
}
