package com.james.LMS;

import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.repository.TopicRepository;
import com.james.LMS.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// @RequiredArgsConstructor
class LmsApplicationTests {
  @Autowired private VideoService videoService;
  @Autowired private CurriculumRepository curriculumRepository;
  @Autowired private TopicRepository topicRepository;

  @Test
  void contextLoads() {
    //    var list = videoService.findAllBySessionIds(List.of(1L));
    //    list.forEach(System.out::println);
    //        var topicIds = this.topicRepository.findAll( PageRequest.of(0, 10));
    //        System.out.println(topicIds.get().toList());
    //    var cur = this.curriculumRepository.findAllInTopicOfUser(List.of(1L,2L,3L),
    // PageRequest.of(0,10));
    //    cur.stream().forEach(System.out::println);
  }
}
