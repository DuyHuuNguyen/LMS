package com.james.LMS.dto;

import lombok.Builder;

@Builder
public record AbortMultiPartUploadDTO(String bucket, String objectKey, String uploadId) {
}
