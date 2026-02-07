package com.james.LMS.util;

import java.time.Duration;

public class DurationConverterUtil {
  public static String toStringDuration(Duration duration) {
    long hours = duration.toHours();
    long minutes = duration.toMinutesPart();
    long seconds = duration.toSecondsPart();
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }
}
