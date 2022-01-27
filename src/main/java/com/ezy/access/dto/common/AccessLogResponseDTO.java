package com.ezy.access.dto.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccessLogResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appName;
    private String pattern;
    private String fileName;
    private String requestKey;
    private long recordsProcessed;
}
