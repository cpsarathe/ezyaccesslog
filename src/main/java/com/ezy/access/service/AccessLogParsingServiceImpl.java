package com.ezy.access.service;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import com.ezy.access.parser.AccessLogFilePatternParser;
import com.ezy.access.parser.ElementParser;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class AccessLogParsingServiceImpl implements AccessLogParsingService {

    @Autowired
    private AccessLogFilePatternParser accessLogFilePatternParser;

    @Autowired
    private List<ElementParser> elementParsers;

    @Override
    public List<AccessLogModelV2> parse(MultipartFile multipartFile, String pattern, String appName) {
        List<AccessLogModelV2> accessLogModels = new ArrayList<>();
        try {
            String requestKey = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
            LogFilePatternInfo logFilePatternInfo = accessLogFilePatternParser.parse(pattern);
            InputStream inputStream = multipartFile.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> list = bufferedReader.lines().collect(Collectors.toList());
            for (String line : list) {
                AccessLogModelV2 accessLogModel = AccessLogModelV2.builder().build();
                for (ElementParser elementParser : elementParsers) {
                    elementParser.parse(line, logFilePatternInfo, accessLogModel);
                }
                accessLogModel.setFileName(multipartFile.getOriginalFilename());
                accessLogModel.setAppName(appName);
                accessLogModel.setRequestKey(requestKey);
                accessLogModels.add(accessLogModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return accessLogModels;
    }
}
