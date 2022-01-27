package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * %{User-Agent}i
 */
@Component
public class IncomingHeaderUserAgentElementParser implements ElementParser {

    private static final String INCOMING_HEADER_USER_AGENT_DEF_CHAR = "{User-Agent}i".toLowerCase(Locale.ROOT);

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(INCOMING_HEADER_USER_AGENT_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), INCOMING_HEADER_USER_AGENT_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String userAgent = elements[index].replace("\"", "");
            accessLogModel.setIUserAgent(userAgent);
        }
    }

}
