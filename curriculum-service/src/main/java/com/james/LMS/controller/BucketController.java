package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.BucketFacade;
import com.james.LMS.request.BucketCriteria;
import com.james.LMS.request.CreateBucketRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.BucketResponse;
import com.james.LMS.response.PaginationResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/buckets")
@RequiredArgsConstructor
public class BucketController {
  private final BucketFacade bucketFacade;

  @Hidden
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Bucket APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
  public BaseResponse<BucketResponse> createBucket(
      @RequestBody @Valid CreateBucketRequest request) {
    return this.bucketFacade.createBucket(request);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Bucket APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
  public BaseResponse<PaginationResponse<BucketResponse>> findAllBuckets(
      @Valid BucketCriteria criteria) {
    return this.bucketFacade.findAllBuckets(criteria);
  }
}
