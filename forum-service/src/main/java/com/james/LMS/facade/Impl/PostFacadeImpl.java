package com.james.LMS.facade.Impl;

import com.james.LMS.facade.PostFacade;
import com.james.LMS.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {
    private final PostService postService;
}
