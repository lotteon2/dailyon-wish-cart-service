package com.dailyon.wishcartservice.common.exception;

public class NotExistsException extends RuntimeException {
    public static final String CART_NOT_FOUND = "존재하지 않는 장바구니 상품입니다";

    public NotExistsException() {
        super();
    }

    public NotExistsException(String message) {
        super(message);
    }

    public NotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistsException(Throwable cause) {
        super(cause);
    }

    protected NotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
