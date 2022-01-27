package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %h
 */
@Component
public class HostElementParser implements ElementParser {

    private static final String REQUEST_HOST_DEF_CHAR = "h";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(REQUEST_HOST_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), REQUEST_HOST_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String host = elements[index].replace("\"","");
            accessLogModel.setRemoteIpAddress(host);
        }
    }

}
