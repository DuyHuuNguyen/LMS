package com.james.LMS;

import com.james.LMS.service.VideoService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// @RequiredArgsConstructor
class LmsApplicationTests {
  @Autowired private VideoService videoService;

  @Test
  void contextLoads() {
    var list = videoService.findAllBySessionIds(List.of(1L));
    list.forEach(System.out::println);
  }
}
