package com.james.LMS.facade;

import com.james.LMS.request.GroupCriteria;
import com.james.LMS.request.UpsertCompanyRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CompanyProfileResponse;
import com.james.LMS.response.GroupResponse;
import com.james.LMS.response.SlicePaginationResponse;

public interface CompanyFacade {
  BaseResponse<Void> upsertCompany(UpsertCompanyRequest request);

  BaseResponse<CompanyProfileResponse> findProfileCompany();

  BaseResponse<SlicePaginationResponse<GroupResponse>> findGroups(GroupCriteria criteria);
}
