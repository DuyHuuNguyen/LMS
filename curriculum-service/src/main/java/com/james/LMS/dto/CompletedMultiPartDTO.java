package com.james.LMS.dto;

import lombok.Builder;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.util.List;

@Builder
public record CompletedMultiPartDTO(String bucket, String objectKey, String uploadId, List<CompletedPart> completedParts )  {
}
