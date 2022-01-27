package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %a
 */
@Component
public class RemoteIPAddressElementParser implements ElementParser {

    private static final String REQUEST_REMOTE_IP_DEF_CHAR = "a";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(REQUEST_REMOTE_IP_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), REQUEST_REMOTE_IP_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String remoteIp = elements[index].replace("\"","");
            accessLogModel.setRemoteIpAddress(remoteIp);
        }
    }

}
