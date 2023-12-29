package com.dailyon.wishcartservice.common.config;

import com.dailyon.wishcartservice.common.exception.CustomFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if(400 <= response.status() && response.status() < 500) {
            throw new CustomFeignException(CustomFeignException.BAD_REQUEST);
        } else if(500 <= response.status()) {
            throw new CustomFeignException(CustomFeignException.PRODUCT_SERVICE_ERROR);
        }

        return errorDecoder.decode(methodKey, response);
    }
}
