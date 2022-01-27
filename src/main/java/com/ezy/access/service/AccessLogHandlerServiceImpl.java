package com.ezy.access.service;

import com.ezy.access.dto.common.AccessLogInputDTO;
import com.ezy.access.dto.common.AccessLogResponseDTO;
import com.ezy.access.model.AccessLogModelV2;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogHandlerServiceImpl implements AccessLogHandlerService {

    @Autowired
    private AccessLogPersistenceService accessLogPersistenceService;
    @Autowired
    private AccessLogParsingService accessLogParsingService;

    @Value("${access.log.request.key.length:10}")
    private int accessLogRequestKeyLength;


    @Override
    public AccessLogResponseDTO uploadAccessLog(AccessLogInputDTO accessLogInputDTO) {
        AccessLogResponseDTO accessLogResponseDTO = AccessLogResponseDTO.builder()
                .appName(accessLogInputDTO.getAppName())
                .pattern(accessLogInputDTO.getPattern())
                .fileName(accessLogInputDTO.getMultipartFile().getOriginalFilename())
                .build();
        accessLogInputDTO.setRequestKey(RandomStringUtils.randomAlphanumeric(accessLogRequestKeyLength).toUpperCase());
        accessLogResponseDTO.setRequestKey(accessLogInputDTO.getRequestKey());
        List<AccessLogModelV2> accessLogModelV2List = accessLogParsingService.parse(accessLogInputDTO);
        accessLogPersistenceService.saveAll(accessLogModelV2List);
        accessLogResponseDTO.setRecordsProcessed(accessLogModelV2List.size());
        return accessLogResponseDTO;
    }

}
