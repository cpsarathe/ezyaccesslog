package com.ezy.access.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Any pattern that we support need to be initialized before startup
 */
@Component
public class ParserRuleConfig {
    private Set<String> parseRuleConfigSet = new LinkedHashSet<>();

    @PostConstruct
    public void init() {
        this.initializeParsingRuleConfigMap();
    }

    private void initializeParsingRuleConfigMap() {
        parseRuleConfigSet.add("h");
        parseRuleConfigSet.add("l");
        parseRuleConfigSet.add("t");
        parseRuleConfigSet.add("u");
        parseRuleConfigSet.add("r");
        parseRuleConfigSet.add("s");
        parseRuleConfigSet.add("b");
        parseRuleConfigSet.add("i");
        parseRuleConfigSet.add("{X-Forwarded-For}i".toLowerCase(Locale.ROOT));
        parseRuleConfigSet.add("{X-Forwarded-Proto}i".toLowerCase(Locale.ROOT));
        parseRuleConfigSet.add("{Host}i".toLowerCase(Locale.ROOT));
        parseRuleConfigSet.add("D");
    }

    public Set<String> getParseRuleConfigSet() {
        return parseRuleConfigSet;
    }

}
