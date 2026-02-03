package com.james.LMS.util;

import com.james.LMS.dto.MessageMailDTO;

public class MailUtil {

  public static MessageMailDTO buildMessageMailDTOForNewUser(String to) {
    MessageMailDTO messageMailDTO =
        MessageMailDTO.builder()
            .to(to)
            .subject("Welcome to LMS systems")
            .content("Welcome to lms system")
            .build();
    return messageMailDTO;
  }

  public static MessageMailDTO buildMessageMailDTOForOTP(String to, String opt) {
    String content = "Your otp :".concat(opt.toString());
    MessageMailDTO messageMailDTO =
        MessageMailDTO.builder().to(to).subject("OPT LMS system").subject(content).build();
    return messageMailDTO;
  }
}
