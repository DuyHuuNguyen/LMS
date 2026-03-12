package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChannelCurriculumsRequest extends BaseCriteria {
  @Hidden private Long channelId;
}
