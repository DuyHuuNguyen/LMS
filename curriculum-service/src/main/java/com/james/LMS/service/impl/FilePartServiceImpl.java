package com.james.LMS.service.impl;

import com.james.LMS.entity.FilePart;
import com.james.LMS.repository.FilePartRepository;
import com.james.LMS.service.FilePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilePartServiceImpl implements FilePartService {
  private final FilePartRepository filePartRepository;

  @Override
  public void save(FilePart filePart) {
    this.filePartRepository.save(filePart);
  }
}
