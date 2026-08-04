package com.james.LMS.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.james.LMS.entity.User;
import com.james.LMS.enums.RoleEnum;
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
  @JsonIgnore private String password;
  @Getter private Collection<? extends GrantedAuthority> authorities;

  public static SecurityUserDetails build(User user, List<GrantedAuthority> authorities) {
    return SecurityUserDetails.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .authorities(authorities)
        .build();
  }

  public static SecurityUserDetails build(User user) {
    return SecurityUserDetails.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  public boolean hasInstructorRole() {
    return this.authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(RoleEnum.INSTRUCTOR.getContent()::equals);
  }

  public boolean hasCompanyAdminRole() {
    return this.authorities.stream()
        .anyMatch(role -> role.getAuthority().equals(RoleEnum.COMPANY_ADMIN.getContent()));
  }
}
