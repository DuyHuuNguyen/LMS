package com.james.LMS.service;

import com.james.LMS.dto.MessageMailDTO;

public interface MailProducerService {
  void send(MessageMailDTO messageMailDTO);
}
