package com.james.LMS.dto;

public interface CompanyGroupDTO {
  Long getGroupId();

  Long getCompanyId();

  String getCompanyName();

  String getGroupName();

  Integer getTotalMembers();

  Integer getMaxGroupSize();
}
