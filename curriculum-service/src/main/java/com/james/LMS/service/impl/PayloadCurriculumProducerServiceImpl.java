package com.james.LMS.service.impl;

import com.james.LMS.dto.InstructorDTO;
import com.james.LMS.dto.PayloadCurriculumVectorMessage;
import com.james.LMS.entity.Curriculum;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.service.InstructorService;
import com.james.LMS.service.PayloadCurriculumProducerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayloadCurriculumProducerServiceImpl implements PayloadCurriculumProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.qdrant-search.topic-exchange}")
    private String topicExchange;

    @Value("${rabbitmq.qdrant-search.routing-key}")
    private String routingKey;

    @Override
    public void sent(PayloadCurriculumVectorMessage payloadCurriculumVectorMessage) {
        log.info("Produce message : {}",payloadCurriculumVectorMessage);
        this.rabbitTemplate.convertAndSend(topicExchange,routingKey,payloadCurriculumVectorMessage);
    }
}
