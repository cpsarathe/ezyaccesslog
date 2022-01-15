package com.ezy.access.dto.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class LogFilePatternInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<String> userDefinedPattern;
    private String patternSplitter;

}
