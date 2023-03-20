package com.hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if(ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");

                // 빈값으로 던지는 의도는 "정상적으로 처리됐다"라는 것을 WAS에게 알려주기 위함
                // 즉, 여기서 예외가 먹었으니 WAS입장에선 예외로 받아들여지지않게 된다.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        // null을 반환하면 예외가 발생한 채로 날라가게 된다.
        return null;
    }
}
