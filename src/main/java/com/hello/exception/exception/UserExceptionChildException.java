package com.hello.exception.exception;

public class UserExceptionChildException extends UserException {
    public UserExceptionChildException() {
        super();
    }

    public UserExceptionChildException(String message) {
        super(message);
    }

    public UserExceptionChildException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExceptionChildException(Throwable cause) {
        super(cause);
    }

    protected UserExceptionChildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
