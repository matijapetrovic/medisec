package com.medisec.hospitalservice.web.config.xss;//package com.medisec.adminservice.web.config;

import com.j2mvc.util.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger("external_request");
    private JSONObject JSON;

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        resetCookies(request,(HttpServletResponse)response);
        String contentType = request.getContentType();
        if (StringUtils.isNotBlank(contentType) && contentType.contains("multipart/form-data")) {
            MultipartHttpServletRequest multipartHttpServletRequest =
                    new CommonsMultipartResolver().resolveMultipart(request);
            XSSRequestWrapper xssNormalRequestWrapper = new XSSRequestWrapper(multipartHttpServletRequest);
            logUrlAndParam(request,xssNormalRequestWrapper,null);
            chain.doFilter(xssNormalRequestWrapper, response);
        } else if (StringUtils.isNotBlank(contentType) && contentType.contains("application/x-www-form-urlencoded")) {
            XSSRequestWrapper xssNormalRequestWrapper = new XSSRequestWrapper(request);
            logUrlAndParam(request,xssNormalRequestWrapper,null);
            chain.doFilter(xssNormalRequestWrapper, response);
        } else if (StringUtils.isNotBlank(contentType) && contentType.contains("application/json")) {
            XSSBodyRequestWrapper xssBodyRequestWrapper = new XSSBodyRequestWrapper(request);
            logUrlAndParam(request,null,xssBodyRequestWrapper);
            chain.doFilter(xssBodyRequestWrapper, response);
        } else {
            logUrlAndParam(request,null,null);
            chain.doFilter(request, response);
        }

    }

    public Cookie[] resetCookies(HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> map = new HashMap<>();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                String value = cookie.getValue();
                if (value != null) {
                    String deleteValue = XSSStripUtils.stripXSS(value);
                    if (!value.equals(deleteValue))
                        map.put(cookie.getName(), cookie.getDomain());
                }
            }

            if (!map.isEmpty()) {
                for (Map.Entry<String, String> cookie : map.entrySet()) {
                    CookieUtil.reMoveCookie(request, response, cookie
                            .getValue(), cookie.getKey(), "");
                }
            }
        }
        return cookies;
    }

    @Override
    public void destroy() {
    }

    private void logUrlAndParam(HttpServletRequest request, XSSRequestWrapper xssNormalRequestWrapper,XSSBodyRequestWrapper xssBodyRequestWrapper) {
        // Get request URL
        String url = request.getRequestURI();
        // Get request method
        String method = request.getMethod();
        // Get path
        String path = request.getQueryString();
        // Get header
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {//Loop through the parameters in the Header and put the traversed parameters into the Map
            String key = (String) headerNames.nextElement();
            String value =  request.getHeader(key);
            map.put(key, value);
        }
        JSON = new JSONObject(map);
        String header = JSON.toString();
        // Get parameters
        String params = "";
        if(xssNormalRequestWrapper != null)
            JSON = new JSONObject(xssNormalRequestWrapper.getParameterMap());
            params = JSON.toString();
        if(xssBodyRequestWrapper != null)
            params = xssBodyRequestWrapper.getBody();

        if(xssNormalRequestWrapper == null && xssBodyRequestWrapper == null) {
            JSON = new JSONObject(request.getParameterMap());
            params = JSON.toString();
        }
        logger.info("Request url:{},method:{},path:{},header:{},param:{}",url,method,path,header,params);
    }


}