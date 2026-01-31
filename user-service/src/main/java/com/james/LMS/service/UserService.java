package com.james.LMS.service;

import com.james.LMS.entity.User;
import java.util.Optional;

public interface UserService {
  Optional<User> findByEmail(String email);
}
