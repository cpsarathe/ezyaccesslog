package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %D
 */
@Component
public class TimeToProcessRequestMillisElementParser implements ElementParser {

    private static final String TIME_PROCESS_REQ_MILLIS_DEF_CHAR = "D";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(TIME_PROCESS_REQ_MILLIS_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), TIME_PROCESS_REQ_MILLIS_DEF_CHAR);
            String[] elements = line.split(logFilePatternInfo.getPatternSplitter());
            String timeToProcessRequestMillis = elements[index].replace("\"", "");
            accessLogModel.setTimeToProcessRequestInMillis(Long.valueOf(timeToProcessRequestMillis));
        }
    }

}
