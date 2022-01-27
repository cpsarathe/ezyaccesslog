package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import org.springframework.stereotype.Component;

/**
 * %u
 */
@Component
public class RemoteUserAuthenticatedElementParser implements ElementParser {

    private static final String REMOTE_USER_AUTHENTICATED_DEF_CHAR = "u";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(REMOTE_USER_AUTHENTICATED_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), REMOTE_USER_AUTHENTICATED_DEF_CHAR);
            String[] elements = this.splitLinePreservingQuotes(line,logFilePatternInfo);
            String remoteUserAuthenticated = elements[index].replace("\"", "");
            accessLogModel.setRemoteUserAuthenticated(remoteUserAuthenticated);
        }
    }

}
