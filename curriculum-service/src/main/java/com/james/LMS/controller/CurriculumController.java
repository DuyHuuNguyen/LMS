package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.CurriculumFacade;
import com.james.LMS.request.*;
import com.james.LMS.response.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/curriculums")
@RequiredArgsConstructor
@Validated
public class CurriculumController {

  private final CurriculumFacade curriculumFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("!hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<CreateCurriculumResponse> createCurriculum(
      @RequestBody @Valid UpsertCurriculumRequest request) {
    return this.curriculumFacade.createCurriculum(request);
  }

  @GetMapping("/review/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_USER')")
  public BaseResponse<CurriculumReviewResponse> reviewCurriculum(@PathVariable("id") Long id) {
    return curriculumFacade.findCurriculumForReviewById(id);
  }

  @GetMapping("/home")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<CurriculumHomeResponse> findCurriculumForHome(
      @Valid @ModelAttribute CurriculumHomeRequest curriculumHomeRequest) {
    return this.curriculumFacade.findCurriculumForHomeNewFlow(curriculumHomeRequest);
  }

  @Hidden
  @GetMapping("/topics")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<TopicResponse>> findAllTopics(
      @Valid TopicCriteria topicCriteria) {
    return this.curriculumFacade.findAllTopicByCriteria(topicCriteria);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<CurriculumResponse>> findCurriculumByTopicId(
      @Valid CurriculumByTopicRequest curriculumByTopicRequest) {
    return this.curriculumFacade.findCurriculumByTopicId(curriculumByTopicRequest);
  }

  // code ng# chua fix
  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<SearchCurriculumResponse>> findAllByCriteria(
      @Valid @ModelAttribute CurriculumCriteria curriculumCriteria) {
    return this.curriculumFacade.findAllByCriteria(curriculumCriteria);
  }

  @GetMapping("/purchased-curriculums")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<PurchasedCurriculumResponse>> findAllPurchasedCurriculums(
      @Valid PurchasedCurriculumCriteria purchasedCurriculumCriteria) {
    return this.curriculumFacade.findAllPurchasedCurriculums(purchasedCurriculumCriteria);
  }

  @GetMapping("/wishlist")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<WishListCurriculumResponse>> findAllWishlist(
      @Valid @NotNull WishlistRequest wishlistRequest) {
    return this.curriculumFacade.findAllWishlist(wishlistRequest);
  }

  @DeleteMapping("/wishlist")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> removeWishlist(@RequestBody @Valid RemoveWishlistRequest request) {
    return this.curriculumFacade.removeWishlist(request);
  }

  @PostMapping("/wishlist/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> addWishlist(@PathVariable("id") Long id) {
    return this.curriculumFacade.addWishList(id);
  }

  @GetMapping("/sessions/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Curriculum APIs"} , summary = "Api for user study")
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("!hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<SessionDetailResponse> findSessionsOfCurriculum(@PathVariable("id") Long id) {
    return this.curriculumFacade.findSessionsOfCurriculum(id);
  }

}
