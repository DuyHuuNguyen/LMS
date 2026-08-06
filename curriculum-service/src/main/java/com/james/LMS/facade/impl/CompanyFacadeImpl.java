package com.james.LMS.facade.impl;

import com.james.LMS.dto.CompanyGroupDTO;
import com.james.LMS.entity.Company;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.exception.EntityNotFoundException;
import com.james.LMS.exception.PermissionDeniedException;
import com.james.LMS.facade.CompanyFacade;
import com.james.LMS.request.GroupCriteria;
import com.james.LMS.request.UpsertCompanyRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CompanyProfileResponse;
import com.james.LMS.response.GroupResponse;
import com.james.LMS.response.SlicePaginationResponse;
import com.james.LMS.service.CompanyService;
import com.james.LMS.service.GroupService;
import com.james.LMS.util.SecurityUserDetailsUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyFacadeImpl implements CompanyFacade {
  private final CompanyService companyService;
  private final GroupService groupService;

  @Override
  @Transactional
  public BaseResponse<Void> upsertCompany(UpsertCompanyRequest request) {
    Company company =
        this.companyService
            .findByUserAdminId(SecurityUserDetailsUtil.PRINCIPAL.getId())
            .orElse(
                Company.builder()
                    .companyName(request.getCompanyName())
                    .userAdminCompanyId(SecurityUserDetailsUtil.PRINCIPAL.getId())
                    .build());

    boolean isCreatedCompany = company.getId() == null;
    if (isCreatedCompany) {
      company.changeCompanyName(request.getCompanyName());
    }

    this.companyService.save(company);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<CompanyProfileResponse> findProfileCompany() {
    Company company =
        this.companyService
            .findByUserAdminId(SecurityUserDetailsUtil.PRINCIPAL.getId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND));

    return BaseResponse.build(
        CompanyProfileResponse.builder()
            .id(company.getId())
            .userAdminCompanyId(company.getUserAdminCompanyId())
            .name(company.getCompanyName())
            .build(),
        true);
  }

  @Override
  public BaseResponse<SlicePaginationResponse<GroupResponse>> findGroups(GroupCriteria criteria) {
    Slice<CompanyGroupDTO> companyGroupDTOSlice =
        this.groupService.findAllByCompanyIdAndUserAdminCompanyId(
            criteria.getCompanyId(),
            SecurityUserDetailsUtil.PRINCIPAL.getId(),
            PageRequest.of(criteria.getCurrentPage(), criteria.getPageSize()));

    if (!companyGroupDTOSlice.hasContent()) {
      throw new PermissionDeniedException(ErrorCode.NO_PERMISSION_ADMIN_COMPANY);
    }

    List<GroupResponse> groupResponses =
        companyGroupDTOSlice
            .get()
            .map(
                companyGroupDTO ->
                    GroupResponse.builder()
                        .id(companyGroupDTO.getGroupId())
                        .userAdminGroupId(SecurityUserDetailsUtil.PRINCIPAL.getId())
                        .maxGroupSize(companyGroupDTO.getMaxGroupSize())
                        .groupName(companyGroupDTO.getGroupName())
                        .totalMembers(companyGroupDTO.getTotalMembers())
                        .build())
            .toList();

    return BaseResponse.build(
        SlicePaginationResponse.<GroupResponse>builder()
            .currentPage(criteria.getCurrentPage())
            .data(groupResponses)
            .build(),
        true);
  }
}
