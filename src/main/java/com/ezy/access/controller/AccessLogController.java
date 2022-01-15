package com.ezy.access.controller;

import com.ezy.access.constants.ResponseStatusEnum;
import com.ezy.access.dto.common.APIResponse;
import com.ezy.access.model.AccessLogModelV2;
import com.ezy.access.service.AccessLogParsingService;
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

import java.util.List;

@RestController
@CommonsLog
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private AccessLogParsingService accessLogParsingService;

    @Autowired
    private ApiResponseWrapper apiResponseWrapper;

    @PostMapping(value = "/upload")
    @ResponseBody
    public APIResponse<String> upload(@RequestParam("file") MultipartFile multipartFile,
                                      @RequestParam(name = "appName") String appName) {
        int totalPersisted = accessLogService.save(multipartFile, appName);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS, "successfully saved " + totalPersisted,
                null);
    }

    @PostMapping(value = "/v1/upload")
    @ResponseBody
    public APIResponse<String> v1Upload(@RequestParam("file") MultipartFile multipartFile,@RequestParam(name = "appName") String appName ,
                                        @RequestParam(name = "pattern") String pattern) {
        List<AccessLogModelV2> accessLogModelV2List = accessLogParsingService.parse(multipartFile,pattern,appName);
        accessLogService.saveAll(accessLogModelV2List);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS, "successfully saved " + 0,
                null);
    }
}
