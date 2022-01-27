package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %l
 */
@Component
public class RemoteLogicUserNameElementParser implements ElementParser {

    private static final String REQUEST_REMOTE_LOGICAL_USER_NAME_DEF_CHAR = "l";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(REQUEST_REMOTE_LOGICAL_USER_NAME_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), REQUEST_REMOTE_LOGICAL_USER_NAME_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String remoteUser = elements[index].replace("\"", "");
            accessLogModel.setRemoteUserIdentd(remoteUser);
        }
    }

}
