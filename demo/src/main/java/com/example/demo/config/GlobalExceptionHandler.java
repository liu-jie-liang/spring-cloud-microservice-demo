package com.example.demo.config;

import com.example.demo.bean.GenericResponse;
import com.example.demo.code.ResponseCode;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @PostConstruct
    public void init() {
        log.info("GlobalExceptionHandler init ...");
    }

    /**
     * 定义要捕获的异常 可以多个 @ExceptionHandler({})     *
     *
     * @param request  request
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(InternalErrorException.class)
    public GenericResponse customExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        InternalErrorException exception = (InternalErrorException) e;

        if (exception.getCode() == ResponseCode.USER_INPUT_ERROR) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else if (exception.getCode() == ResponseCode.FORBIDDEN) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new GenericResponse(exception.getCode(), null, exception.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public GenericResponse tokenExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        log.error("token exception", e);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new GenericResponse(ResponseCode.AUTHENTICATION_NEEDED);
    }

}
