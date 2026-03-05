package com.james.LMS;

import com.james.LMS.dto.BannerDTO;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.repository.TopicRepository;
import com.james.LMS.repository.UserTopicRepository;
import com.james.LMS.service.BannerService;
import com.james.LMS.service.VideoService;
import com.james.LMS.util.chain_responsibility.client.OwnerExamClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

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
  @Autowired private BannerService bannerService;


  @Test
  void bannerTest(){
    BannerDTO bannerDTO = BannerDTO.builder().id(UUID.randomUUID().toString()).index(1).imageUrl("https://img-c.udemycdn.com/notices/featured_carousel_slide/image_responsive/5ff9a88e-d19b-45d7-a19e-04798c979b32.png").build();
    bannerService.storeWithoutTimeout(bannerDTO);


    BannerDTO bannerDTO1 = BannerDTO.builder().id(UUID.randomUUID().toString()).index(2).imageUrl("https://img-c.udemycdn.com/notices/featured_carousel_slide/image_responsive/b81740e9-3a76-4517-b5b6-9899802b4166.jpg").build();
    bannerService.storeWithoutTimeout(bannerDTO1);
  }


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
    //    this.curriculumRepository
    //        .findAllPurchasedCurriculums(1L, PageRequest.of(0, 10))
    //        .forEach(
    //            p -> {
    //              log.info("test {}", p.toString());
    //            });
    //
    //    this.curriculumRepository.isPurchasedCurriculumToHaveVideo(2L, 2l, 2L, 2L);

    var boo = this.curriculumRepository.isExistedChannelAndCurriculumForUploadVideo(2L, 1L, 1L);
    log.info("boooooo {}", boo);
    //      Thread t1 = new Thread(() -> { ownerExamClient.validUserHasExamInCurriculum(req);});
    //    Thread t2 = new Thread(() -> { java.validUserHasExamInCurriculum(req);});
    //    t1.start();
    //    t2.start();
  }
}
