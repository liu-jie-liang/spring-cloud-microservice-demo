package com.example.demo.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestContextUtils {

    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(ra)) {
            return null;
        }
        HttpServletRequest request = null;
        if (ra instanceof ServletRequestAttributes) {
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            request = sra.getRequest();
        }
        return request;
    }

    public static String getParam(String name) {
        HttpServletRequest request = getRequest();
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        return request.getParameter(name);
    }
}
