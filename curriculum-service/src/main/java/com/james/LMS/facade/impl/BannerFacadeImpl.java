package com.james.LMS.facade.impl;

import com.james.LMS.dto.BannerDTO;
import com.james.LMS.enums.FileType;
import com.james.LMS.facade.BannerFacade;
import com.james.LMS.request.UploadBannerRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.service.BannerService;
import com.james.LMS.service.CloudinaryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerFacadeImpl implements BannerFacade {
  private final BannerService bannerService;
  private final CloudinaryService cloudinaryService;

  @Override
  public BaseResponse<Void> uploadBanner(UploadBannerRequest request) {
    String imageUrl = this.cloudinaryService.uploadFile(request.getBytes(), FileType.IMAGE);
    BannerDTO bannerDTO =
        BannerDTO.builder()
            .id(UUID.randomUUID().toString())
            .index(request.getIndex())
            .imageUrl(imageUrl)
            .build();
    this.bannerService.storeWithoutTimeout(bannerDTO);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<List<BannerDTO>> findAllBanners() {
    return BaseResponse.build(bannerService.findAll().stream().sorted().toList(), true);
  }

  @Override
  public BaseResponse<Void> hiddenBannerById(String id) {
    this.bannerService.hiddenBannerById(id);
    return BaseResponse.ok();
  }
}
