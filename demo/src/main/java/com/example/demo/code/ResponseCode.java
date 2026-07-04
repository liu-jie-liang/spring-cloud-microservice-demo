package com.example.demo.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    SUCCESS(0, "Success"),

    INTERNAL_ERROR(1, "服务器内部错误"),

    USER_INPUT_ERROR(2, "用户输入错误"),

    AUTHENTICATION_NEEDED(3, "Token过期或无效"),

    FORBIDDEN(4, "禁止访问"),

    TOO_FREQUENT_VISIT(5, "访问太频繁，请休息一会儿");

    private final int code;

    private final String message;

    private final HttpStatus status;

    ResponseCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    ResponseCode(int code, String message) {
        this(code, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
