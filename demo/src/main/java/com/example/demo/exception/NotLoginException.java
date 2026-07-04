package com.example.demo.exception;

import com.example.demo.code.ResponseCode;
import lombok.Getter;

@Getter
public class NotLoginException extends RuntimeException {

    private final ResponseCode code;

    public NotLoginException() {
        super(String.format("%s", ResponseCode.AUTHENTICATION_NEEDED.getMessage()));
        this.code = ResponseCode.AUTHENTICATION_NEEDED;
    }

    public NotLoginException(Throwable e) {
        super(e);
        this.code = ResponseCode.AUTHENTICATION_NEEDED;
    }

    public NotLoginException(String msg) {
        this(ResponseCode.AUTHENTICATION_NEEDED, msg);
    }

    public NotLoginException(ResponseCode code) {
        super(String.format("%s", code.getMessage()));
        this.code = code;
    }

    public NotLoginException(ResponseCode code, String msg) {
        super(msg);
        this.code = code;
    }

}
