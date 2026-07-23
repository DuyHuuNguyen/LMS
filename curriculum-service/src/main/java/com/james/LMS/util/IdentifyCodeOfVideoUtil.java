package com.james.LMS.util;

import com.james.LMS.enums.IdentifyTemplate;

public class IdentifyCodeOfVideoUtil {

  public static String genVideoIdentifyCode(String email, String videoName) {
    return String.format(
        IdentifyTemplate.IDENTIFY_CODE_TEMPLATE.getTemplate(),
        email,
        HashMD5Util.encryptMd5(videoName));
  }
}
