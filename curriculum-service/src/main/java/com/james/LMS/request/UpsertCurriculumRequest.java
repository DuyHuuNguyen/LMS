package com.james.LMS.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertCurriculumRequest {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Headline must not be blank")
    @Size(max = 500, message = "Headline must not exceed 500 characters")
    private String headLine;

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be greater than or equal to 0")
    private BigDecimal cost;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @NotBlank(message = "Requirement must not be blank")
    @Size(max = 3000, message = "Requirement must not exceed 3000 characters")
    private String requirement;

    @NotBlank(message = "Thumbnail must not be blank")
    @Size(max = 500, message = "Thumbnail must not exceed 500 characters")
    private String thumbnail;

    private Long chanelId;
}