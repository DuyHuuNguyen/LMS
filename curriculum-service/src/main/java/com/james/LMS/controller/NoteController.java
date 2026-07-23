package com.james.LMS.controller;

import com.james.LMS.config.SecurityConfig;
import com.james.LMS.facade.NoteFacade;
import com.james.LMS.request.NoteCriteria;
import com.james.LMS.request.UpsertNoteRequest;
import com.james.LMS.response.BaseResponse;
import com.james.LMS.response.NoteResponse;
import com.james.LMS.response.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
  private final NoteFacade noteFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Note APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> createNote(@RequestBody @Valid UpsertNoteRequest request) {
    return this.noteFacade.createNote(request);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Note APIs"})
  @SecurityRequirement(name = SecurityConfig.SECURITY_REQUIREMENT)
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<NoteResponse>> findAllNotes(
      @NotNull NoteCriteria noteCriteria) {
    return this.noteFacade.findAllNote(noteCriteria);
  }
}
