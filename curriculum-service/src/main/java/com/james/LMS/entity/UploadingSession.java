package com.james.LMS.entity;

import com.james.LMS.enums.UploadingSessionStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "uploading_sessions")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UploadingSession extends BaseEntity {
  @Column(name = "part_size")
  private Long partSize;

  @Column(name = "total_parts")
  private Integer totalParts;

  @Column(name = "s3_upload_id", length = 200)
  private String s3UploadId;

  @Column(name = "bucket", length = 200)
  private String bucket;

  @Column(name = "object_key", length = 256)
  private String objectKey;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 100)
  private UploadingSessionStatus status;

  @OneToMany(mappedBy = "uploadingSession", fetch = FetchType.LAZY)
  @Builder.Default
  private List<FilePart> partFiles = new ArrayList<>();

  public void completeUpload() {
    this.status = UploadingSessionStatus.COMPETED;
  }

  public void abortUpload() {
    this.status = UploadingSessionStatus.CANCELLATION;
  }
}
