package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.UploadingLargeFileFacade;
import com.james.LMS.request.InitialUploadLargeFileSessionRequest;
import com.james.LMS.request.UploadFileChunkRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.InitialUploadLargeFileSessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/uploading-large-files")
@RequiredArgsConstructor
public class UploadingLargeFileController {

  private final UploadingLargeFileFacade uploadingLargeFileFacade;

  @PostMapping("/starting-session")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Upload large file APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<InitialUploadLargeFileSessionResponse> initialUploadLargeFileSession(
      @RequestBody @Valid InitialUploadLargeFileSessionRequest request) {
    return uploadingLargeFileFacade.initialUploadLargeFileSession(request);
  }

  @PostMapping(
      value = "/chunk/{id}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Upload large file APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  public BaseResponse<Void> uploadChunkFile(
      @PathVariable("id") Long id,
      @RequestPart("chunk") MultipartFile chunk,
      @ModelAttribute UploadFileChunkRequest request) {
    request.withId(id);
    request.withChunk(chunk);
    return this.uploadingLargeFileFacade.uploadChunkFile(request);
  }

  @PostMapping("/completion/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Upload large file APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  BaseResponse<Void> completeUploadFile(@PathVariable("id") Long id) {
    return this.uploadingLargeFileFacade.completeUploadFile(id);
  }

  @PostMapping("/cancellation/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Upload large file APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
  BaseResponse<Void> cancellationUploadFile(@PathVariable("id") Long id) {
    return this.uploadingLargeFileFacade.cancellationUploadFile(id);
  }
}
