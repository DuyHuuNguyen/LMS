package com.james.LMS.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_TOPIC_NOT_FOUND("2000", "Not found followed topics of user"),
  CURRICULUM_NOT_FOUND("2001", "Curriculum not found"),
  EXAM_NOT_FOUND("2002", "Exam not found"),
  SESSION_NOT_FOUND("2003", "Session not found"),
  PERMISSION_DENIED_VIDEO("2004", "Video is denied"),
  VIDEO_NOT_FOUND_AT_STORAGE("2005", "Video is not found at storage"),
  INSTRUCTOR_NOT_FOUND("2006", "Instructor not found"),
  UPLOADING_VIDEO_IS_DENIED("2007", "Denied uploading video"),
  BANNER_NOT_FOUND("2008", "Banner not found"),
  FILE_ERROR_UPLOAD("2009", "Upload file error"),
  VIDEO_WAS_UPLOADED_INTO_STORAGE("3000", "Video was uploaded into storage"),
  VIDEO_METADATA_NOT_FOUND("3001", "Video metadata not found"),
  SESSION_OR_VIDEO_NOT_FOUND("3002", "Video or session not found"),
  WISHLIST_NOT_FOUND("3002", "Wishlist not found"),
  CREATED_WISH_LIST("3003", "Created wishlist"),
  UPLOADING_SESSION_NOT_FOUND("3004", "Uploading session not found"),
  THE_FILE_CHUNKS_MUST_BE_UPLOADED_IN_SEQUENTIAL_ORDER(
      "3005", "The file chunks must be uploaded in sequential order"),
  COMPLETE_UPLOAD_ERROR("3006", "Complete uploading file error"),
  ABORT_UPLOAD_ERROR("3007", "Abort uploading error"),
  BUCKET_NOT_FOUND("3008", "Bucket not found"),
  CHANNEL_NOT_FOUND("3009", "Channel not found"),
  COMPANY_NOT_FOUND("3010", "Company not found"),
  NO_PERMISSION_ADMIN_COMPANY("3011", "no permission admin company"),
  NO_PERMISSION_ACCESS_TO_GROUP("3012", "No permission to access the group"),
  GROUP_NOT_FOUND("3013", "Group not found");
  private final String code;
  private final String message;
}
