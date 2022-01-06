package com.ezy.access.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface AccessLogService {

    public void save(MultipartFile multipartFile) throws IOException;
}
