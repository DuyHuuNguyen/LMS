package com.james.LMS.facade;

import com.james.LMS.dto.BannerDTO;
import com.james.LMS.request.UploadBannerRequest;
import com.james.LMS.response.BaseResponse;
import java.util.List;

public interface BannerFacade {
  BaseResponse<Void> uploadBanner(UploadBannerRequest request);

  BaseResponse<List<BannerDTO>> findAllBanners();

  BaseResponse<Void> hiddenBannerById(String id);
}
