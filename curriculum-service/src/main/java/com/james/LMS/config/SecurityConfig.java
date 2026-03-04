package com.james.LMS.config;

import com.james.LMS.interceptor.AuthenticationTokenProviderInterceptor;
import com.james.LMS.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

  private final AuthService authService;

  public static final String COOKIE_SECURITY_NAME = "access-token";
  public static final String COOKIE_REFRESH_TOKEN_NAME = "refresh-token";

  private final String[] WHITE_LISTS = {
    "/swagger-ui/**", "/v3/api-docs/**",
  };

  public static final String SECURITY_REQUIREMENT = "Bearer Authentication";

  @Bean
  public AuthenticationTokenProviderInterceptor authenticationTokenProviderInterceptor() {
    return new AuthenticationTokenProviderInterceptor(this.authService);
  }

  @Bean
  @SneakyThrows
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> {})
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            request ->
                request.requestMatchers(WHITE_LISTS).permitAll().anyRequest().authenticated());
    http.addFilterBefore(
        this.authenticationTokenProviderInterceptor(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
