package com.example.demo.exception;

import com.example.demo.code.ResponseCode;
import lombok.Getter;

@Getter
public class InternalErrorException extends RuntimeException {

    private final ResponseCode code;

    public InternalErrorException() {
        super(String.format("%s", ResponseCode.INTERNAL_ERROR.getMessage()));
        this.code = ResponseCode.INTERNAL_ERROR;
    }

    public InternalErrorException(Throwable e) {
        super(e);
        this.code = ResponseCode.INTERNAL_ERROR;
    }

    public InternalErrorException(String msg) {
        this(ResponseCode.INTERNAL_ERROR, msg);
    }

    public InternalErrorException(ResponseCode code) {
        super(String.format("%s", code.getMessage()));
        this.code = code;
    }

    public InternalErrorException(ResponseCode code, String msg) {
        super(msg);
        this.code = code;
    }

}
