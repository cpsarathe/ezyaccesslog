package com.ezy.access.service;

import com.ezy.access.model.AccessLogModel;
import com.ezy.access.model.AccessLogModelV2;
import com.ezy.access.repository.AccessLogRepository;
import com.ezy.access.repository.AccessLogV2Repository;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class AccessLogPersistenceServiceImpl implements AccessLogPersistenceService {

    @Value("${ezyaccess.log.persist.batch.size:500}")
    protected int logPersistBatchSize;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private AccessLogV2Repository accessLogV2Repository;

    public int save(MultipartFile multipartFile, String appName) {
        int totalPersisted = 0;
        try {
            log.info("file upload started with filename " + multipartFile.getOriginalFilename() + " for appName " + appName);
            InputStream inputStream = multipartFile.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> list = bufferedReader.lines().collect(Collectors.toList());
            log.info("records to be persisted " + list.size());
            List<AccessLogModel> accessLogModels = new ArrayList<>();
            int count = 0;
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
                        .appName(appName)
                        .fileName(multipartFile.getOriginalFilename())
                        .build();
                accessLogModels.add(accessLogModel);
                count++;
                if (count == logPersistBatchSize) {
                    accessLogRepository.saveAll(accessLogModels);
                    accessLogModels.clear();
                    totalPersisted += count;
                    log.info("persisted so far " + totalPersisted);
                    count = 0;
                }
            }

            if (!accessLogModels.isEmpty()) {
                log.info("records still exist not in multiple of " + logPersistBatchSize);
                accessLogRepository.saveAll(accessLogModels);
                accessLogModels.clear();
                totalPersisted += count;
                log.info("persisted so far " + totalPersisted);
            }

            list = null; //hi gc

        } catch (Exception ex) {
            log.error("Error saving access log with fileName: " + multipartFile.getName(), ex);
        }
        log.info("finished saving access log");
        return totalPersisted;
    }

    @Override
    public int saveAll(List<AccessLogModelV2> accessLogModelV2List) {
        int totalRecords = accessLogModelV2List.size();
        int total = totalRecords;
        log.info("total records to process " + total);
        int  start = 0;
        int  end = totalRecords > logPersistBatchSize ? logPersistBatchSize : totalRecords ;
        while(totalRecords > 0 && start < end) {
            List<AccessLogModelV2> subList = accessLogModelV2List.subList(start,end);
            accessLogV2Repository.saveAll(subList);
            log.info("start:"+start+" end:"+end+" persisted:"+subList.size() + " remaining:"+totalRecords );
            start = start + logPersistBatchSize + 1;
            end = (start + logPersistBatchSize) > total ? total - 1 :(start + logPersistBatchSize) ;
            totalRecords = totalRecords - logPersistBatchSize;
        }
        return accessLogModelV2List.size();
    }


    private String getUri(List<String> copyList) {
        String s = copyList.get(4) + " " + copyList.get(5) + " " + copyList.get(6);
        return s.length() > 255 ? s.substring(0, 255) : s;
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
