package com.ezy.access.service;

import org.springframework.web.multipart.MultipartFile;

public interface AccessLogService {

    public int save(MultipartFile multipartFile, String appName);
}
