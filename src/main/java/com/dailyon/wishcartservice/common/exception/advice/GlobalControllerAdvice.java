package com.dailyon.wishcartservice.common.exception.advice;

import com.dailyon.wishcartservice.common.exception.CustomFeignException;
import com.dailyon.wishcartservice.common.exception.ErrorResponse;
import com.dailyon.wishcartservice.common.exception.NotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validException(BindException e) {
        return ErrorResponse.builder()
                .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                .build();
    }

    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notExistsException(NotExistsException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(CustomFeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse feignException(CustomFeignException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }
}
