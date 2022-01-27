package com.ezy.access.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@Builder
public class AccessLogInputDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appName;
    private String pattern;
    @JsonIgnoreProperties(ignoreUnknown=true)
    private MultipartFile multipartFile;
    private String requestKey;
    private long recordsProcessed;
}
