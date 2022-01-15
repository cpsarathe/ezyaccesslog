package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %i
 */
@Component
public class IncomingHeaderElementParser implements ElementParser {

    private static final String INCOMING_HEADER_DEF_CHAR = "i";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(INCOMING_HEADER_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), INCOMING_HEADER_DEF_CHAR);
            String[] elements = line.split(logFilePatternInfo.getPatternSplitter());
            String incomingHeader = elements[index].replace("\"", "");
            accessLogModel.setIXForwardedFor(incomingHeader);
        }
    }

}
