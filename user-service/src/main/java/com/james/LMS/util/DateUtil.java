package com.james.LMS.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtil {
  public static LocalDate convertToLocalDate(long timestamp) {
    try {
      return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    } catch (Exception e) {
      return null;
    }
  }
}
