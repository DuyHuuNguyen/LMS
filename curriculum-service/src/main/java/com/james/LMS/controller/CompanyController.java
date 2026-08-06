package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.CompanyFacade;
import com.james.LMS.request.GroupCriteria;
import com.james.LMS.request.UpsertCompanyRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.CompanyProfileResponse;
import com.james.LMS.response.GroupResponse;
import com.james.LMS.response.SlicePaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {
  private final CompanyFacade companyFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Company APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  BaseResponse<Void> createCompany(@RequestBody @Valid UpsertCompanyRequest request) {
    return this.companyFacade.upsertCompany(request);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Company APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updateCompany(@RequestBody @Valid UpsertCompanyRequest request) {
    return this.companyFacade.upsertCompany(request);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Company APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<CompanyProfileResponse> findProfileCompany() {
    return this.companyFacade.findProfileCompany();
  }

  @GetMapping("/groups/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Company APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  BaseResponse<SlicePaginationResponse<GroupResponse>> findGroups(
      @PathVariable Long id, @NotNull @Valid GroupCriteria criteria) {
    criteria.setCompanyId(id);
    return this.companyFacade.findGroups(criteria);
  }
}
