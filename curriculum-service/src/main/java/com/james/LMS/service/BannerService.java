package com.james.LMS.service;

import com.james.LMS.dto.BannerDTO;
import java.util.List;

public interface BannerService {
  void hiddenBannerById(String id);

  List<BannerDTO> findAll();

  void storeWithoutTimeout(BannerDTO bannerDTO);

}
