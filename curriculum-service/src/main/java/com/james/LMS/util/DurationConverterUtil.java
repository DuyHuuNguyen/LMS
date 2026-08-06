package com.james.LMS.util;

import java.time.*;

public class DurationConverterUtil {
  public static String toStringDuration(Duration duration) {
    long hours = duration.toHours();
    long minutes = duration.toMinutesPart();
    long seconds = duration.toSecondsPart();
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  public record MonthRange(Long startOfMonth, Long endOfMonth) {}

  public static MonthRange getMonthRange(Month month, String timeZone) {
    int year = Year.now().getValue();
    ZoneId zoneId = ZoneId.of(timeZone);

    Long start = LocalDate.of(year, month, 1).atStartOfDay(zoneId).toInstant().toEpochMilli();

    Long end =
        LocalDate.of(year, month, month.length(Year.isLeap(year)))
            .atTime(LocalTime.MAX)
            .atZone(zoneId)
            .toInstant()
            .toEpochMilli();

    return new MonthRange(start, end);
  }

  public static Long getStartOfMonth(Month month, String timeZone) {
    int year = Year.now().getValue();
    ZoneId zoneId = ZoneId.of(timeZone);
    return LocalDate.of(year, month, 1).atStartOfDay(zoneId).toInstant().toEpochMilli();
  }

  public static Long getEndOfMonth(Month month, String timeZone) {
    int year = Year.now().getValue();
    ZoneId zoneId = ZoneId.of(timeZone);
    return LocalDate.of(year, month, month.length(Year.isLeap(year)))
        .atTime(LocalTime.MAX)
        .atZone(zoneId)
        .toInstant()
        .toEpochMilli();
  }

  public static LocalDateTime getLocalDateTimeFromLong(Long value, String timeZone) {
    return Instant.ofEpochMilli(value).atZone(ZoneId.of(timeZone)).toLocalDateTime();
  }

  public static Long toEpochMilli(LocalDateTime localDateTime, String timeZoneId) {
    if (localDateTime == null || timeZoneId == null || timeZoneId.isBlank()) {
      return null;
    }

    return localDateTime.atZone(ZoneId.of(timeZoneId)).toInstant().toEpochMilli();
  }
}
