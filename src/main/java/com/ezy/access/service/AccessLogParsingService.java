package com.ezy.access.service;

import com.ezy.access.model.AccessLogModelV2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccessLogParsingService {

    public List<AccessLogModelV2> parse(MultipartFile multipartFile , String pattern , String appName) ;
}
