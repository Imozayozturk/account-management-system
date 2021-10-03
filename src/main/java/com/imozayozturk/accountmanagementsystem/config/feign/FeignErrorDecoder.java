package com.imozayozturk.accountmanagementsystem.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        return Optional.ofNullable(response)
                .map(resp -> {
                    HttpStatus httpStatus = HttpStatus.valueOf(resp.status());
                    LOGGER.error("Api call failed methodKey: " + methodKey + "http-status: " + httpStatus.value() + " reason: " + response.reason());
                    return httpStatus;
                })
                .map(httpStatus -> new RuntimeException("Feign client error"))
                .orElse(null);
    }
}
