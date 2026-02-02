package com.james.LMS.service.impl;

import com.james.LMS.dto.MessageMailDTO;
import com.james.LMS.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;

  @Override
  public void send(MessageMailDTO messageMailDTO) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      helper.setTo(messageMailDTO.getTo());
      helper.setSubject(messageMailDTO.getSubject());
      helper.setText(messageMailDTO.getContent(), true);

      if (messageMailDTO.getFrom() != null) {
        helper.setFrom(messageMailDTO.getFrom());
      }

      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new RuntimeException("Failed to send email", e);
    }
  }
}
