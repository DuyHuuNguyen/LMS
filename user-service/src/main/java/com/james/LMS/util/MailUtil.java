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

  public static MessageMailDTO buildMessageMailDTOForOTP(String to, String otp) {
    String content = "Your otp :"+ otp;
    MessageMailDTO messageMailDTO =
        MessageMailDTO.builder().to(to).subject("OPT LMS system").subject(content).content(content).build();
    return messageMailDTO;
  }
}
