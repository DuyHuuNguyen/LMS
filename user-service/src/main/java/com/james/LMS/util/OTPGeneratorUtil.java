package com.james.LMS.util;

import java.util.Random;

public class OTPGeneratorUtil {
  public static String generaRandomCode() {
    Random random = new Random();
    int code = random.nextInt(9999);
    return String.format("%04d", code);
  }
}
