package com.james.LMS.dto;

import lombok.Builder;

import java.io.InputStream;

@Builder
public record UploadingPartDTO(
        String bucket,
        String objectKey,
        String uploadId,
        int partNumber,
        InputStream inputStream,
        long contentLength ) {

}
