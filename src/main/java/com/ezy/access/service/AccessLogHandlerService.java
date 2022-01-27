package com.ezy.access.service;

import com.ezy.access.dto.common.AccessLogInputDTO;
import com.ezy.access.dto.common.AccessLogResponseDTO;

public interface AccessLogHandlerService {

    public AccessLogResponseDTO uploadAccessLog(AccessLogInputDTO accessLogInputDTO);

}
