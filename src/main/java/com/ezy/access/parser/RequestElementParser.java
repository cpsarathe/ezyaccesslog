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
            String[] elements = line.split(logFilePatternInfo.getPatternSplitter());
            String httpMethod = elements[index];
            String uri = elements[index + 1];
            String httpSpec = elements[index + 2];

            httpMethod = httpMethod.replace("\"", "");
            uri = uri.replace("\"", "");
            httpSpec = httpSpec.replace("\"", "");
            accessLogModel.setRequest(httpMethod + " " + uri + " " + httpSpec);
        }
    }
}
