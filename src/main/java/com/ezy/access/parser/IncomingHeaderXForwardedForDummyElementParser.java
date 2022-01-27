package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Added this class as a workaround for the case where source log sends splitted IPs comma separated and it was not wraped under ""
 * {X-Forwarded-For-Dummy}i
 */
@Component
public class IncomingHeaderXForwardedForDummyElementParser implements ElementParser {

    public static final String INCOMING_HEADER_X_FORWARDED_FOR_DEF_CHAR = "{X-Forwarded-For-Dummy}i".toLowerCase(Locale.ROOT);

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
//        if (logFilePatternInfo.getUserDefinedPattern().contains(INCOMING_HEADER_X_FORWARDED_FOR_DEF_CHAR)) {
//            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), INCOMING_HEADER_X_FORWARDED_FOR_DEF_CHAR);
//            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
//            String incomingHeader = elements[index].replace("\"", "").trim() + "," + elements[index + 1].replace("\"", "").trim();
//            String ixForwardedFor = accessLogModel.getIXForwardedFor();
//            accessLogModel.setIXForwardedFor(incomingHeader + "," + ixForwardedFor);
//        }
    }

}
