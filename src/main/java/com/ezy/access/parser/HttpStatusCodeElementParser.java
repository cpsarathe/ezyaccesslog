package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %s
 */
@Component
public class HttpStatusCodeElementParser implements ElementParser {

    private static final String HTTP_STATUS_CODE_DEF_CHAR = "s";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(HTTP_STATUS_CODE_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), HTTP_STATUS_CODE_DEF_CHAR);
            String[] elements = line.split(logFilePatternInfo.getPatternSplitter());
            String statusCode = elements[index].replace("\"", "");
            accessLogModel.setStatusCode(Integer.valueOf(statusCode));
        }
    }

}
