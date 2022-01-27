package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %r
 */
@Component
public class RequestElementParser implements ElementParser {
    private static final String REQUEST_REQUEST_ELEMENT_DEF_CHAR = "r";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(REQUEST_REQUEST_ELEMENT_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), REQUEST_REQUEST_ELEMENT_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String request = elements[index];
            request = request.replace("\"","");
            String[] reqArray = request.split(" ");
            String httpMethod = reqArray[0];
            String uri = reqArray[1];
            String httpSpec = elements[2];
            accessLogModel.setRequestMethod(httpMethod);
            accessLogModel.setRequest(uri.length() > 255 ? uri.substring(0,255) : uri);
        }
    }
}
