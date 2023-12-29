package com.dailyon.wishcartservice.common.exception;

public class CustomFeignException extends RuntimeException {
    public static String PRODUCT_SERVICE_ERROR = "상품 서비스 연결 실패";
    public static String BAD_REQUEST = "잘못된 정보 요청";
    public CustomFeignException() {
        super();
    }

    public CustomFeignException(String message) {
        super(message);
    }

    public CustomFeignException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomFeignException(Throwable cause) {
        super(cause);
    }

    protected CustomFeignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
