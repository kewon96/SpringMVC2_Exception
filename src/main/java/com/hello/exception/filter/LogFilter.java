package com.hello.exception.filter;

import com.hello.exception.log.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("log filter init");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uuid = UUID.randomUUID().toString();

        LogUtils.createMDC(uuid, httpRequest);
        try {
            log.info("REQUEST [{}][{}][{}]", MDC.get("uuid"), MDC.get("dispatcherType"), MDC.get("requestURI"));
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}][{}]", MDC.get("uuid"), MDC.get("dispatcherType"), MDC.get("requestURI"));
        }
    }
    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
