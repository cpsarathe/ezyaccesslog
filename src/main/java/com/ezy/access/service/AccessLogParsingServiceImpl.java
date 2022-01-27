package com.ezy.access.service;

import com.ezy.access.dto.common.AccessLogInputDTO;
import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogErrorModel;
import com.ezy.access.model.AccessLogModelV2;
import com.ezy.access.parser.AccessLogFilePatternParser;
import com.ezy.access.parser.ElementParser;
import com.ezy.access.repository.AccessLogErrorRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class AccessLogParsingServiceImpl implements AccessLogParsingService {

    @Autowired
    private AccessLogFilePatternParser accessLogFilePatternParser;

    @Autowired
    private List<ElementParser> elementParsers;

    @Autowired
    private AccessLogErrorRepository accessLogErrorRepository;

    @Override
    public List<AccessLogModelV2> parse(AccessLogInputDTO accessLogInputDTO) {
        List<AccessLogModelV2> accessLogModels = new ArrayList<>();
        try {
            LogFilePatternInfo logFilePatternInfo = accessLogFilePatternParser.parse(accessLogInputDTO.getPattern());
            InputStream inputStream = accessLogInputDTO.getMultipartFile().getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> list = bufferedReader.lines().collect(Collectors.toList());
            for (String line : list) {
                try {
                    if (canIgnore(line)) continue;
                    AccessLogModelV2 accessLogModel = AccessLogModelV2.builder().build();
                    line = prepareLineForParsing(line);
                    for (ElementParser elementParser : elementParsers) {
                        elementParser.parse(line, logFilePatternInfo, accessLogModel);
                    }
                    accessLogModel.setFileName(accessLogInputDTO.getMultipartFile().getOriginalFilename());
                    accessLogModel.setAppName(accessLogInputDTO.getAppName());
                    accessLogModel.setRequestKey(accessLogInputDTO.getRequestKey());
                    accessLogModel.setCreatedDate(Calendar.getInstance().getTime());
                    accessLogModels.add(accessLogModel);
                } catch (Exception ex) {
                    logError(line,accessLogInputDTO);
                    log.error("Error processing line of a file  " + line, ex);
                }

            }
        } catch (Exception ex) {
            log.error("Error parsing uploaded file " + accessLogInputDTO.getMultipartFile().getOriginalFilename(), ex);
        }
        return accessLogModels;
    }

    private void logError(String line, AccessLogInputDTO accessLogInputDTO) {
        AccessLogErrorModel accessLogErrorModel = AccessLogErrorModel.builder()
                .accessLogRecord(line)
                .appName(accessLogInputDTO.getAppName())
                .fileName(accessLogInputDTO.getMultipartFile().getOriginalFilename())
                .requestKey(accessLogInputDTO.getRequestKey())
                .createdDate(Calendar.getInstance().getTime())
                .build();
        accessLogErrorRepository.save(accessLogErrorModel);
    }

    private boolean canIgnore(String line) {
        return line.contains("HEAD");
    }

//    public static void main(String[] args) {
//        String text = "47.91.113.67,-,[25/Jan/2022:00:00:09 +0400],-,\"GET /admin/login HTTP/1.1\",200,4245,202.5.139.2, 47.91.113.67,https,22,citysouq.dubaistore.com";
//        int lastHttpIndex = text.lastIndexOf("https");
//        String strBeforeHttps = text.substring(0, lastHttpIndex);
//        String ipSplits[] = strBeforeHttps.split(",");
//        int x = ipSplits.length - 1;
//        StringBuilder ipBuilder = new StringBuilder();
//        ipBuilder.append("\"");
//        while (x > 0 && ipSplits[x].contains(".")) {
//            ipBuilder.append(ipSplits[x].trim());
//            ipBuilder.append(",");
//            x--;
//        }
//        String ip = ipBuilder.substring(0,ipBuilder.length() - 1) + "\"";
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < x; i++) {
//            sb.append(ipSplits[i]);
//            sb.append(",");
//        }
//        sb.append(ip);
//        sb.append(",");
//        String tt = text.substring(lastHttpIndex);
//
//        System.out.println(sb.toString() + tt);
//    }

    private String prepareLineForParsing(String text) {
        int lastHttpIndex = text.lastIndexOf("https");
        String strBeforeHttps = text.substring(0, lastHttpIndex);
        String ipSplits[] = strBeforeHttps.split(",");
        int x = ipSplits.length - 1;
        StringBuilder ipBuilder = new StringBuilder();
        ipBuilder.append("\"");
        while (x > 0 && ipSplits[x].contains(".")) {
            ipBuilder.append(ipSplits[x].trim());
            ipBuilder.append(",");
            x--;
        }
        String ip = ipBuilder.substring(0, ipBuilder.length() - 1) + "\"";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= x; i++) {
            sb.append(ipSplits[i]);
            sb.append(",");
        }
        sb.append(ip);
        sb.append(",");
        String tt = text.substring(lastHttpIndex);
        return sb.toString() + tt;
    }
}
