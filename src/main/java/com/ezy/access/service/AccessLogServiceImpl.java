package com.ezy.access.service;

import com.ezy.access.model.AccessLogModel;
import com.ezy.access.repository.AccessLogRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public void save(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> list = bufferedReader.lines().collect(Collectors.toList());
        List<AccessLogModel> accessLogModels = new ArrayList<>();
        for (String line : list) {
            List<String> copyList = parseLineAndArrangeOrder(line);
            AccessLogModel accessLogModel = AccessLogModel.builder()
                    .hRemoteHost(copyList.get(0))
                    .tDateTime(convertStringToDate(copyList.get(2), "dd/MMM/yyyy:hh:mm:ss"))
                    .rRequestUri(getUri(copyList))
                    .sHttpStatusCode(Integer.valueOf(copyList.get(7)))
                    .bResponseSize(copyList.get(8).equals("-") ? 0 : Integer.valueOf(copyList.get(8)))
                    .iXForwardedFor(copyList.get(9))
                    .iXForwardedProto(copyList.get(10))
                    .createdDate(Calendar.getInstance().getTime())
                    .build();
            accessLogModels.add(accessLogModel);
        }
        accessLogRepository.saveAll(accessLogModels);
    }

    private String getUri(List<String> copyList) {
        String s = copyList.get(4) + " " + copyList.get(5) + " " + copyList.get(6);
        return s.length()>255 ? s.substring(0, 255) : s;
    }

    private List<String> parseLineAndArrangeOrder(String line) {
        if (StringUtils.isNotEmpty(line)) {
            List<String> copyList = new ArrayList<>();
            String[] logs = line.split(" ");
            for (String next : logs) {
                if (StringUtils.isEmpty(next) || next.contains("]")) {
                    continue;
                }
                if (next.contains("[")) {
                    copyList.add(next.replace("[", ""));
                } else if (next.contains("\"")) {
                    copyList.add(next.replace("\"", ""));
                } else if (next.contains(",")) {
                    copyList.add(next.replace(",", ""));
                } else {
                    copyList.add(next);
                }
            }
            return copyList;
        }
        return Collections.emptyList();
    }

    private Date convertStringToDate(String date, String format) {
        if (date != null) {
            try {
                return new SimpleDateFormat(format).parse(date);
            } catch (java.text.ParseException e) {
                log.error("Error while converting Date", e);
            }
        }
        return null;
    }
}
