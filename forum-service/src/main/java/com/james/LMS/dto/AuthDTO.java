package com.james.LMS.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AuthDTO {
    private Long id;
    private String email;
    private List<String> roles;
}
