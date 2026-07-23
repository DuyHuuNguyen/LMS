 package com.james.LMS.request;

 import io.swagger.v3.oas.annotations.Hidden;
 import lombok.*;
 import org.springframework.web.multipart.MultipartFile;

 @AllArgsConstructor
 @NoArgsConstructor
 @ToString
 @Data
 public class UploadFileChunkRequest {

  @Hidden
  private Long uploadingSessionId;
  private Integer partNumber;
  @Hidden
  private MultipartFile chunk;

  public void withId(Long uploadingSessionId){
   this.uploadingSessionId = uploadingSessionId;
  }

  public void withChunk(MultipartFile chunk){
   this.chunk = chunk;
  }
 }
