package com.james.LMS;

import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.repository.TopicRepository;
import com.james.LMS.repository.UserTopicRepository;
import com.james.LMS.service.VideoService;
import com.james.LMS.util.chain_responsibility.client.OwnerExamClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@Slf4j
@SpringBootTest
// @RequiredArgsConstructor
class LmsApplicationTests {
  @Autowired private VideoService videoService;
  @Autowired private CurriculumRepository curriculumRepository;
  @Autowired private TopicRepository topicRepository;
  @Autowired private UserTopicRepository userTopicRepository;
  @Autowired private OwnerExamClient ownerExamClient;
  @Autowired private OwnerExamClient java;

  @Test
  void contextLoads() {
    //    var list = videoService.findAllBySessionIds(List.of(1L));
    //    list.forEach(System.out::println);
    //        var topicIds = this.topicRepository.findAll( PageRequest.of(0, 10));
    //        System.out.println(topicIds.get().toList());
    //    var cur = this.curriculumRepository.findAllInTopicOfUser(List.of(1L,2L,3L),
    // PageRequest.of(0,10));
    //    cur.stream().forEach(System.out::println);
    //    var bo = this.userTopicRepository.existsByUserIdAndTopic_IdAndIsActiveIsTrue(1L, 7L);
    //    log.info(" boolean {}", bo);
  }

  @Test
  public void demo() {
    log.info("Run test");
    this.curriculumRepository
        .findAllPurchasedCurriculums(1L, PageRequest.of(0, 10))
        .forEach(
            p -> {
              log.info("test {}", p.toString());
            });

    //      Thread t1 = new Thread(() -> { ownerExamClient.validUserHasExamInCurriculum(req);});
    //    Thread t2 = new Thread(() -> { java.validUserHasExamInCurriculum(req);});
    //    t1.start();
    //    t2.start();
  }
}
