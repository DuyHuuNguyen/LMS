package com.james.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class EtagPartNumberDTO {
    private String etag;
    private Integer partNumber;
}
