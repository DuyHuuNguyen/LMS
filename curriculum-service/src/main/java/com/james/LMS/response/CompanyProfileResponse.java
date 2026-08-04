package com.james.LMS.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class CompanyProfileResponse {
  private Long id;
  private Long userAdminCompanyId;
  private String name;
}
