package com.ezy.access.parser;

import com.ezy.access.config.ParserRuleConfig;
import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.exception.AccessLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * parse pattern passed from UI e.g
 * e.g. %h %l %t %u "%r" %s %b %{X-Forwarded-For}i %{X-Forwarded-Proto}i
 */
@Component
public class AccessLogFilePatternParser {

    @Autowired
    private ParserRuleConfig parserRuleConfig;

    public LogFilePatternInfo parse(String pattern) {
        String[] patterns;
        LogFilePatternInfo logFilePatternInfo = new LogFilePatternInfo();
        Set<String> set = new LinkedHashSet<>();
        if (pattern.contains(",")) {
            patterns = pattern.split(",");
            logFilePatternInfo.setPatternSplitter(",");
        } else {
            patterns = pattern.split(" ");
            logFilePatternInfo.setPatternSplitter(" ");
        }
        Set<String> parseRuleConfigSet = parserRuleConfig.getParseRuleConfigSet();
        for (String s : patterns) {
            String a = s.replace("%", "").replace("\"","").trim();
            if(a.contains("{") && a.contains("}")) {
                a = a.toLowerCase(Locale.ROOT);
            }
            if (parseRuleConfigSet.contains(a)) {
                set.add(a);
            } else {
                throw new AccessLogException("Error parsing pattern , undefine pattern found " + s);
            }
        }
        logFilePatternInfo.setUserDefinedPattern(set);
        return logFilePatternInfo;
    }

}
