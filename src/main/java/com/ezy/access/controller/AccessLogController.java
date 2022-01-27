package com.ezy.access.controller;

import com.ezy.access.constants.ResponseStatusEnum;
import com.ezy.access.dto.common.APIResponse;
import com.ezy.access.dto.common.AccessLogInputDTO;
import com.ezy.access.dto.common.AccessLogResponseDTO;
import com.ezy.access.service.AccessLogHandlerService;
import com.ezy.access.service.AccessLogPersistenceService;
import com.ezy.access.wrapper.ApiResponseWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@CommonsLog
public class AccessLogController {

    @Autowired
    private AccessLogPersistenceService accessLogService;

    @Autowired
    private AccessLogHandlerService accessLogHandlerService;

    @Autowired
    private ApiResponseWrapper apiResponseWrapper;

    @PostMapping(value = "/upload")
    @Deprecated
    @ResponseBody
    public APIResponse<String> upload(@RequestParam("file") MultipartFile multipartFile,
                                      @RequestParam(name = "appName") String appName) {
        int totalPersisted = accessLogService.save(multipartFile, appName);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS, "successfully saved " + totalPersisted,
                null);
    }

    @PostMapping(value = "/v1/upload")
    @ResponseBody
    public APIResponse<AccessLogResponseDTO> v1Upload(@RequestParam("file") MultipartFile multipartFile,
                                                      @RequestParam(name = "appName") String appName,
                                                      @RequestParam(name = "pattern") String pattern) {
        AccessLogInputDTO accessLogInputDTO = AccessLogInputDTO.builder()
                .appName(appName).multipartFile(multipartFile).pattern(pattern)
                .build();
        AccessLogResponseDTO accessLogResponseDTO = null;
        try {
            accessLogResponseDTO = accessLogHandlerService.uploadAccessLog(accessLogInputDTO);
            return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS, "successfully uploaded",
                    accessLogResponseDTO);
        } catch (Exception ex) {
            String uuid = UUID.randomUUID().toString();
            String msg = "Error while processing your request,ref#" + uuid;
            log.error(msg, ex);
            return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SYSTEM_ERROR,
                    msg, accessLogResponseDTO);
        }

    }
}
