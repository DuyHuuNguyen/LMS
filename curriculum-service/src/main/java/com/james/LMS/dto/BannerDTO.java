package com.james.LMS.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class BannerDTO  implements  Comparable<BannerDTO>{
    private String id;
    private Integer index;
    @NotBlank
    private String imageUrl;
    @Hidden
    @Builder.Default
    private Boolean isActive = true;

    @Override
    public int compareTo(@NotNull BannerDTO bannerDTO) {
        return this.index - bannerDTO.index;
    }
}
