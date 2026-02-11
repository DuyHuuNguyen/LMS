package com.james.LMS;

import com.james.LMS.entity.User;
import com.james.LMS.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class LmsApplicationTests {

  @Autowired UserRepository userRepository;

  @Test
  void contextLoads() {}

  @Test
  void runUser() {
    User user = this.userRepository.findUserAndInstructorById(115L).get();
    log.info("User {}", user);
  }
}
