package com.james.LMS.service.impl;

import com.james.LMS.repository.UserGivenRepository;
import com.james.LMS.service.UserGivenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGIvenServiceImpl implements UserGivenService {
  private final UserGivenRepository userGivenRepository;
}
