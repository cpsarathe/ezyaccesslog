package com.ezy.access.controller;

import com.ezy.access.constants.ResponseStatusEnum;
import com.ezy.access.dto.common.APIResponse;
import com.ezy.access.service.AccessLogService;
import com.ezy.access.wrapper.ApiResponseWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CommonsLog
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private ApiResponseWrapper apiResponseWrapper;

    @PostMapping(value = "/upload")
    @ResponseBody
    public APIResponse<String> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        accessLogService.save(multipartFile);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS, "successful",
                null);
    }
}
