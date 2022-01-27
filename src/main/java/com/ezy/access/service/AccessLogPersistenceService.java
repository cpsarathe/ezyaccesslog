package com.ezy.access.service;

import com.ezy.access.model.AccessLogModelV2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccessLogPersistenceService {

    public int save(MultipartFile multipartFile, String appName);

    public int saveAll(List<AccessLogModelV2> accessLogModelV2List);
}
