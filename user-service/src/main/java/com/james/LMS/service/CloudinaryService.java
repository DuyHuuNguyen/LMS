package com.james.LMS.service;

import com.james.LMS.enums.FileType;

public interface CloudinaryService {
    String uploadFile(byte[] image, FileType resourceType);
}
