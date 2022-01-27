package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * %S
 */
@Component
public class SessionIdElementParser implements ElementParser {

    private static final String SESSION_ID_DEF_CHAR = "S";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(SESSION_ID_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), SESSION_ID_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String sessionId = elements[index].replace("\"", "");
            accessLogModel.setSessionId(sessionId);
        }
    }

}
