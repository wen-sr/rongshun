package com.rongshun.common;

import com.rongshun.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HttpInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger log = LoggerFactory.getLogger(HttpInterceptor.class);
    private static final String START_TIME = "requestStartTime";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception
    {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
        long start = System.currentTimeMillis();
        request.setAttribute("requestStartTime", Long.valueOf(start));
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception
    {
        removeThreadLocalInfo();
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        String url = request.getRequestURI().toString();
        long start = ((Long)request.getAttribute("requestStartTime")).longValue();
        long end = System.currentTimeMillis();
        log.info("request completed. url:{}, cost:{}", url, Long.valueOf(end - start));

        removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo() {}
}
