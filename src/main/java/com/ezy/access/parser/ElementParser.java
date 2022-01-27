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

    default String[] splitLinePreservingQuotes(String line, LogFilePatternInfo logFilePatternInfo) {
        String[] elements = line.split(logFilePatternInfo.getPatternSplitter() + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//        // merge proto
//        Set<String> set = logFilePatternInfo.getUserDefinedPattern();
//        int index = 0;
//        for (String s : set) {
//            if (s.contains(IncomingHeaderXForwardedForElementParser.INCOMING_HEADER_X_FORWARDED_FOR_DEF_CHAR)) {
//                break;
//            }
//            index++;
//        }
//        String[] newElements = new String[elements.length - 1];
//        int m = 0;
//        for (int x = 0; x <= newElements.length; x++) {
//            if (x == index) {
//                newElements[m++] = elements[x] + "," + elements[x + 1];
//                x++;
//            } else {
//                newElements[m++] = elements[x];
//            }
//        }
        return elements;
    }
}
