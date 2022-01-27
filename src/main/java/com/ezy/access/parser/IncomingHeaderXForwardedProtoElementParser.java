package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * %{X-Forwarded-Proto}i
 */
@Component
public class IncomingHeaderXForwardedProtoElementParser implements ElementParser {

    private static final String INCOMING_HEADER_X_FORWARDED_PROTO_DEF_CHAR = "{X-Forwarded-Proto}i".toLowerCase(Locale.ROOT);

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(INCOMING_HEADER_X_FORWARDED_PROTO_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), INCOMING_HEADER_X_FORWARDED_PROTO_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String incomingHeader = elements[index].replace("\"", "");
            accessLogModel.setIXForwardedProto(incomingHeader);
        }
    }

}
