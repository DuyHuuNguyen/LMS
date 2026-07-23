package com.james.LMS;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.AuthDTO;
import com.james.LMS.dto.BannerDTO;
import com.james.LMS.enums.IdentifyTemplate;
import com.james.LMS.facade.VideoFacade;
import com.james.LMS.repository.CurriculumRepository;
import com.james.LMS.repository.NoteRepository;
import com.james.LMS.repository.TopicRepository;
import com.james.LMS.repository.UserTopicRepository;
import com.james.LMS.request.VideoUploadingPresignUrlRequest;
import com.james.LMS.service.BannerService;
import com.james.LMS.service.VideoService;
import com.james.LMS.util.HashMD5Util;
import com.james.LMS.util.chain_responsibility.client.OwnerExamClient;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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

  @Autowired private VideoFacade videoFacade;

  @Autowired private NoteRepository noteRepository;

  @Test
  public void testFindByCriteria() {}

  @Test
  public void run() {
    noteRepository.findAllByUserIdAndCurriculumIdWithIsActiveIsTrue(2L, 2L, PageRequest.of(0, 10));
  }

  //  @Test
  //  void run() {
  //
  //    log.info(
  //        "{}",
  //        this.curriculumRepository.isPurchasedCurriculum(
  //            ValidUserPurchasedCurriculumAccessDTO.builder()
  //                .userId(1L)
  //                .curriculumId(1000L)
  //                .build()));
  //  }

  //    @Test
  //    void channelCurriculum(){
  //     var pa= PageRequest.of(0,10);
  //     this.curriculumRepository.findAllInChannel(1L,pa).stream().forEach(p -> {
  //       log.debug(p.toString());
  //     });
  //    }

  @Test
  public void testUploadVideo() {
    var principle =
        SecurityUserDetails.build(
            AuthDTO.builder()
                .id(2L)
                .email("23130075@st.hcmuaf.edu.vn")
                .roles(List.of("ROLE_USER", "ROLE_INSTRUCTOR"))
                .build());

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(principle, null, null);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    VideoUploadingPresignUrlRequest request =
        VideoUploadingPresignUrlRequest.builder()
            .videoName("Code java")
            .curriculumId(1L)
            .sessionId(1L)
            .isPreView(true)
            .index(20)
            .durationSeconds(1000L)
            .build();
    var response = this.videoFacade.generateVideoUploadPresignUrl(request);
    log.info("test resp {}", response);
  }

  @Test
  void testMD5() {
    String identifyCode =
        String.format(
            IdentifyTemplate.IDENTIFY_CODE_TEMPLATE.getTemplate(),
            "23130075@st.hcmuaf.edu.vn",
            HashMD5Util.encryptMd5("hc code java spring boot"));
    log.info(identifyCode, "23130075@st.hcmuaf.edu.vn_8b301e80f2b5306678d7cd87d7a39472");
  }

  @Test
  void bannerTest() {
    BannerDTO bannerDTO =
        BannerDTO.builder()
            .id(UUID.randomUUID().toString())
            .index(1)
            .imageUrl(
                "https://img-c.udemycdn.com/notices/featured_carousel_slide/image_responsive/5ff9a88e-d19b-45d7-a19e-04798c979b32.png")
            .build();
    bannerService.storeWithoutTimeout(bannerDTO);

    BannerDTO bannerDTO1 =
        BannerDTO.builder()
            .id(UUID.randomUUID().toString())
            .index(2)
            .imageUrl(
                "https://img-c.udemycdn.com/notices/featured_carousel_slide/image_responsive/b81740e9-3a76-4517-b5b6-9899802b4166.jpg")
            .build();
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
