package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.dto.BannerDTO;
import com.james.LMS.facade.BannerFacade;
import com.james.LMS.request.UploadBannerRequest;
import com.james.LMS.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/banners")
@RequiredArgsConstructor
public class BannerController {
  private final BannerFacade bannerFacade;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Banner APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<BannerDTO>> findAllBanners() {
    return bannerFacade.findAllBanners();
  }

  @PostMapping(consumes = "multipart/form-data", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Upload image",
      tags = {"Banner APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
  @SneakyThrows
  public BaseResponse<Void> uploadBanner(
      @RequestPart("image") MultipartFile image, @RequestBody UploadBannerRequest request) {
    request.withBytes(image);
    return this.bannerFacade.uploadBanner(request);
  }

  @PatchMapping("/hidden-banner/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Banner APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
  public BaseResponse<Void> hiddenBanner(@PathVariable String id) {
    return this.bannerFacade.hiddenBannerById(id);
  }
}
