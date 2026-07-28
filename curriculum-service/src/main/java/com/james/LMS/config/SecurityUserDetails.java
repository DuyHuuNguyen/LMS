package com.james.LMS.config;

import com.james.LMS.dto.AuthDTO;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityUserDetails implements UserDetails {
  @Getter @Setter public Long id;
  private String email;
  @Getter @Builder.Default private String timeZone = "Asia/Ho_Chi_Minh";
  @Getter private Collection<? extends GrantedAuthority> authorities;

  public static SecurityUserDetails build(AuthDTO authDTO, List<GrantedAuthority> authorities) {
    return SecurityUserDetails.builder()
        .id(authDTO.getId())
        .email(authDTO.getEmail())
        .authorities(authorities)
        .build();
  }

  public static SecurityUserDetails build(AuthDTO authDTO) {
    return SecurityUserDetails.builder().id(authDTO.getId()).email(authDTO.getEmail()).build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.email;
  }
}
