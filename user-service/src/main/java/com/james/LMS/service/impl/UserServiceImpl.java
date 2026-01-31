package com.james.LMS.service.impl;

import com.james.LMS.entity.User;
import com.james.LMS.repository.UserRepository;
import com.james.LMS.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public Optional<User> findByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }
}
