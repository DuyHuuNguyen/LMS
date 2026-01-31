package com.james.LMS.service.impl;

import com.james.LMS.entity.User;
import com.james.LMS.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  @Override
  public Optional<User> findByEmail(String email) {
    return Optional.empty();
  }
}
