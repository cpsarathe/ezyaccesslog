package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;

import java.util.Set;

public interface ElementParser {

    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel);

    default int getIndex(Set<String> userDefinedPattern, String rule) {
        int x = 0;
        for (String str : userDefinedPattern) {
            if (str.equals(rule)) {
                return x;
            }
            x++;
        }
        return x;
    }
}
