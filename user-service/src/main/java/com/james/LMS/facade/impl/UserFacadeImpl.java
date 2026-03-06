package com.james.LMS.facade.impl;

import com.james.LMS.config.SecurityUserDetails;
import com.james.LMS.dto.MessageMailDTO;
import com.james.LMS.entity.Instructor;
import com.james.LMS.entity.Role;
import com.james.LMS.entity.User;
import com.james.LMS.enums.*;
import com.james.LMS.exception.*;
import com.james.LMS.facade.UserFacade;
import com.james.LMS.request.*;
import com.james.LMS.response.*;
import com.james.LMS.service.*;
import com.james.LMS.util.DateUtil;
import com.james.LMS.util.MailUtil;
import com.james.LMS.util.OTPGeneratorUtil;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final JwtService jwtService;
  private final CacheService cacheService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private final MailProducerService mailProducerService;
  private final CloudinaryService cloudinaryService;
  private final ChannelService channelService;

  @Override
  public BaseResponse<LoginResponse> login(LoginRequest loginRequest) {
    var authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    var accessToken = jwtService.generateAccessToken(loginRequest.getEmail());
    var refreshToken = jwtService.generateRefreshToken(loginRequest.getEmail());

    var accessTokenCacheKey =
        String.format(TokenType.ACCESS_TOKEN.getCacheKeyTemplate(), loginRequest.getEmail());
    var refreshTokenCacheKey =
        String.format(TokenType.REFRESH_TOKEN.getCacheKeyTemplate(), loginRequest.getEmail());

    cacheService.store(accessTokenCacheKey, accessToken, 1, TimeUnit.HOURS);
    cacheService.store(refreshTokenCacheKey, refreshToken, 14, TimeUnit.DAYS);

    log.info("Login response");
    return BaseResponse.build(
        LoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build(), true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> signUp(UpsertUserRequest upsertUserRequest) {
    boolean isExistUser = this.userService.existsUserByEmail(upsertUserRequest.getEmail());
    if (isExistUser) throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXISTS);

    String passwordEncoded = this.passwordEncoder.encode(upsertUserRequest.getPassword());
    User user =
        User.builder()
            .username(upsertUserRequest.getUsername())
            .email(upsertUserRequest.getEmail())
            .password(passwordEncoded)
            .build();

    Role userRole =
        this.roleService
            .findByRoleName(RoleEnum.USER)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND));
    user.addRole(userRole);

    this.userService.save(user);

    this.mailProducerService.send(MailUtil.buildMessageMailDTOForNewUser(user.getEmail()));
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String email = this.jwtService.getEmailFromJwtToken(refreshTokenRequest.getRefreshToken());
    String refreshTokenKey = String.format(TokenType.REFRESH_TOKEN.getCacheKeyTemplate(), email);

    boolean isInvalidRefreshToken = !this.cacheService.hasKey(refreshTokenKey);
    if (isInvalidRefreshToken) throw new PermissionDeniedException(ErrorCode.JWT_INVALID);

    String accessToken = this.jwtService.generateAccessToken(email);

    RefreshTokenResponse refreshTokenResponse =
        RefreshTokenResponse.builder().accessToken(accessToken).build();
    return BaseResponse.build(refreshTokenResponse, true);
  }

  @Override
  public BaseResponse<Void> logout() {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String accessTokenCacheKey =
        String.format(TokenType.ACCESS_TOKEN.getCacheKeyTemplate(), principal.getUsername());
    String refreshTokenCacheKey =
        String.format(TokenType.REFRESH_TOKEN.getCacheKeyTemplate(), principal.getUsername());

    cacheService.delete(accessTokenCacheKey);
    cacheService.delete(refreshTokenCacheKey);

    SecurityContextHolder.clearContext();
    return BaseResponse.ok();
  }

  @Override
  @Transactional
  public BaseResponse<Void> resetPassword(ResetPasswordRequest resetPasswordRequest) {
    var isValidPassword =
        resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword());
    if (!isValidPassword) throw new PermissionDeniedException(ErrorCode.NOT_MATCHED_PASSWORD);

    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user =
        userService
            .findByEmail(principal.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    var newPasswordEncoded = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
    user.changePassword(newPasswordEncoded);

    userService.save(user);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<ForgotPasswordResponse> forgotPassword(
      ForgotPasswordRequest forgotPasswordRequest) {
    String timeOutRetryKey =
        String.format(
            ResetPasswordKey.TIMEOUT_RETRY_KEY.getContent(), forgotPasswordRequest.getEmail());
    boolean isValidForgotPassword = this.cacheService.hasKey(timeOutRetryKey);

    if (isValidForgotPassword)
      throw new SpamForgotPasswordException(ErrorCode.SPAM_FORGOT_PASSWORD);

    User user =
        userService
            .findByEmail(forgotPasswordRequest.getEmail())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    String otp = OTPGeneratorUtil.generaRandomCode();
    MessageMailDTO messageMailDTO = MailUtil.buildMessageMailDTOForOTP(user.getEmail(), otp);

    this.mailProducerService.send(messageMailDTO);
    String otpKey = String.format(ResetPasswordKey.OTP_KEY.getContent(), user.getEmail());

    this.cacheService.store(
        timeOutRetryKey, forgotPasswordRequest.getEmail(), 10, TimeUnit.MINUTES);
    this.cacheService.store(otpKey, otp, 10, TimeUnit.MINUTES);
    return BaseResponse.build(ForgotPasswordResponse.builder().build(), true);
  }

  @Override
  public BaseResponse<VerifyOTPResponse> verify(VerifyOTPRequest verifyOTPRequest) {
    User user =
        userService
            .findByEmail(verifyOTPRequest.getEmail())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    String otpKey = String.format(ResetPasswordKey.OTP_KEY.getContent(), user.getEmail());

    Object otp = this.cacheService.retrieve(otpKey);

    boolean isValidOTP = otp != null;
    if (!isValidOTP) throw new OTPTimeOutException(ErrorCode.OTP_TIMEOUT);

    boolean isMatchedOtp = otp.equals(verifyOTPRequest.getOtp());
    if (!isMatchedOtp) throw new PermissionDeniedException(ErrorCode.NOT_MATCHED_OTP);

    String resetPasswordToken = this.jwtService.generateResetPasswordToken(user.getEmail());

    return BaseResponse.build(
        VerifyOTPResponse.builder().resetPasswordToken(resetPasswordToken).build(), true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> instruct(InstructionRequest instructionRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    boolean isAlreadyInstructor =
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(RoleEnum.INSTRUCTOR.getContent()::equals);
    if (isAlreadyInstructor)
      throw new PermissionDeniedException(ErrorCode.INSTRUCTOR_ALREADY_EXISTS);

    User user =
        userService
            .findByEmail(principal.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    Instructor instructor =
        Instructor.builder()
            .name(instructionRequest.getName())
            .about(instructionRequest.getAbout())
            .build();
    user.addInstructor(instructor);

    Role instructorRole =
        this.roleService
            .findByRoleName(RoleEnum.INSTRUCTOR)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND));

    user.addRole(instructorRole);

    this.userService.save(user);
    return BaseResponse.ok();
  }

  @Override
  @Transactional(readOnly = true)
  public BaseResponse<UserDetailResponse> findProfile() {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user =
        userService
            .findByEmail(principal.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    boolean isInstructor = user.getInstructor() != null;
    Long channelId = null;
    if (isInstructor) {
      channelId = this.channelService.findChannelIdByUserId(principal.getId());
    }
    UserDetailResponse userDetailResponse =
        UserDetailResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .avatarUrl(user.getAvatarUrl())
            .isInstructor(isInstructor)
            .channelId(channelId)
            .createdAt(DateUtil.convertToLocalDate(user.getCreatedAt()))
            .instructorAbout(user.getInstructor().getAbout())
            .instructorName(user.getInstructor().getName())
            .build();
    return BaseResponse.build(userDetailResponse, true);
  }

  @Override
  @Transactional(readOnly = true)
  public BaseResponse<UserDetailResponse> findDetailById(Long id) {
    User user =
        userService
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    boolean isInstructor = user.getInstructor() != null;

    UserDetailResponse userDetailResponse =
        UserDetailResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .avatarUrl(user.getAvatarUrl())
            .isInstructor(isInstructor)
            .createdAt(DateUtil.convertToLocalDate(user.getCreatedAt()))
            .instructorAbout(user.getInstructor().getAbout())
            .instructorName(user.getInstructor().getName())
            .build();
    return BaseResponse.build(userDetailResponse, true);
  }

  @Override
  @Transactional
  public BaseResponse<String> uploadFile(byte[] bytes) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user =
        userService
            .findByEmail(principal.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    String avatarUrl = this.cloudinaryService.uploadFile(bytes, FileType.IMAGE);
    user.addAvatarUrl(avatarUrl);
    return BaseResponse.build(avatarUrl, true);
  }

  @Override
  @Transactional
  public BaseResponse<Void> updateProfile(UpdateUserProfileRequest updateUserProfileRequest) {
    SecurityUserDetails principal =
        (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user =
        userService
            .findByEmail(principal.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    user.changeUsername(updateUserProfileRequest.getUsername());

    boolean isInstructor = user.getInstructor() != null;
    if (isInstructor) {
      user.changeInstructorName(updateUserProfileRequest.getInstructorName());
      user.changeInstructorAbout(updateUserProfileRequest.getInstructorAbout());
    }

    this.userService.save(user);
    return BaseResponse.ok();
  }
}
