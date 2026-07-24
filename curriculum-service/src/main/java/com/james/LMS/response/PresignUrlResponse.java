package com.james.LMS.response;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PresignUrlResponse {
    private String presignUrl;
    private Integer pausedAt;
}
