package com.hello.exception.log;

import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

public class LogUtils {

    public static void createMDC(String uuid, HttpServletRequest request) {
        MDC.put("uuid", uuid);
        MDC.put("requestURI", request.getRequestURI());
        MDC.put("dispatcherType", request.getDispatcherType().name());
    }

}
