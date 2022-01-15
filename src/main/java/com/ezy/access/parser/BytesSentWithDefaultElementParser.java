package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %b
 */
@Component
public class BytesSentWithDefaultElementParser implements ElementParser {

    private static final String BYTES_SENT_DEFAULT_DEF_CHAR = "b";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(BYTES_SENT_DEFAULT_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), BYTES_SENT_DEFAULT_DEF_CHAR);
            String[] elements = line.split(logFilePatternInfo.getPatternSplitter());
            String byteSent = elements[index].replace("\"", "");
            accessLogModel.setBytesSentWithDefault(Long.valueOf(byteSent));
        }
    }

}
