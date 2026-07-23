package com.james.LMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "part_files")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FilePart extends BaseEntity {
  @Column(name = "part_number")
  private Integer partNumber;

  @Column(name = "content_length")
  private Long contentLength;

  @Column(name = "etag", length = 200)
  private String etag;

  @ManyToOne
  @JoinColumn(name = "uploading_session_id")
  private UploadingSession uploadingSession;
}
