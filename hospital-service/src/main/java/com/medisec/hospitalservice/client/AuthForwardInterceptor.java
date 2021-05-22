package com.medisec.hospitalservice.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class AuthForwardInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        requestTemplate.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
    }
}
