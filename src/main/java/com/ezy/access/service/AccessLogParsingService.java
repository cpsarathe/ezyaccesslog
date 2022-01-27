package com.ezy.access.service;

import com.ezy.access.dto.common.AccessLogInputDTO;
import com.ezy.access.model.AccessLogModelV2;

import java.util.List;

public interface AccessLogParsingService {

    public List<AccessLogModelV2> parse(AccessLogInputDTO accessLogInputDTO);
}
