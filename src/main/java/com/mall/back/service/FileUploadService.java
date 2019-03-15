package com.mall.back.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String uploadFile(String path, MultipartFile file);
}
