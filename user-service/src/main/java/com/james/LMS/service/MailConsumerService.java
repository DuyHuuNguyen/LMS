package com.james.LMS.service;

import com.james.LMS.dto.MessageMailDTO;

public interface MailConsumerService {
  void consume(MessageMailDTO messageMailDTO);
}
