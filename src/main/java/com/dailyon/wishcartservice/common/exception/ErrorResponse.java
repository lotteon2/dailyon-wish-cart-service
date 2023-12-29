package com.dailyon.wishcartservice.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;

    @Builder
    public ErrorResponse(String message) {
        this.message = message;
    }
}
