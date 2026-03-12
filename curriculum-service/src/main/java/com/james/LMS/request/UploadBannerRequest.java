package com.james.LMS.request;

import io.swagger.v3.oas.annotations.Hidden;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UploadBannerRequest {
  @Hidden private byte[] bytes;
  private Integer index;

  @Hidden
  public void withBytes(MultipartFile file) throws IOException {
    this.bytes = file.getBytes();
  }
}
