package com.james.LMS.service.impl;

import com.james.LMS.repository.PostParagraphRepository;
import com.james.LMS.service.PostParagraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostParagraphServiceImpl implements PostParagraphService {
    private final PostParagraphRepository postParagraphRepository;
}
