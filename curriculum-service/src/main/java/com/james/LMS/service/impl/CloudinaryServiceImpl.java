package com.james.LMS.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.james.LMS.enums.ErrorCode;
import com.james.LMS.enums.FileType;
import com.james.LMS.exception.UploadFileException;
import com.james.LMS.service.CloudinaryService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(byte[] image, FileType resourceType) {

        var params = ObjectUtils.asMap("folder", "banners", "resource_type", resourceType.getType());
        try {
            var uploadResult = cloudinary.uploader().upload(image, params);
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new UploadFileException(ErrorCode.FILE_ERROR_UPLOAD);
        }
    }
}
