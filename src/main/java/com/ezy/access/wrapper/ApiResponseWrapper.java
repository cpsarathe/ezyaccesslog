package com.ezy.access.wrapper;

import com.ezy.access.constants.ResponseStatusEnum;
import com.ezy.access.dto.common.APIResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;


@CommonsLog
@Component
public class ApiResponseWrapper {

    public <T> APIResponse<T> wrapResponse(int statusCode, ResponseStatusEnum status, String message, T responseObj) {
        APIResponse<T> response = new APIResponse<>();
        if (responseObj != null) {
            response.setBody(responseObj);
        }
        response.setStatus(status.getName().toLowerCase());
        response.setMessage(message);
        response.setStatusCode(statusCode);
        return response;
    }

}
