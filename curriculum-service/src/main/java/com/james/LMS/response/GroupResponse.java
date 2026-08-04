package com.james.LMS.response;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
  private Long id;
  private Long userAdminGroupId;
  private Integer maxGroupSize;
  private String groupName;
  private Integer totalMembers;
}
