package com.hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.exception.common.ErrorResult;
import com.hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if(ex instanceof UserException) {
                log.info("UserException resolver to 400");

                // 이후 처리는 요청부류에 따라 달라져야한다.
                // JSON인 경우와 반대의 경우

                String accept = request.getHeader("accept");

                // 여기서 400 CODE를 주입함으로서 이후 Servlet Container에서 오류로 인식한다.
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if("application/json".equals(accept)) {
                    ErrorResult result = new ErrorResult(ex);
                    String errorResultJson = result.convertToJson(objectMapper);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");

                    // 응답으로 JSON을 반환하면 WAS까지 예외전달하지않는다.
                    response.getWriter().write(errorResultJson);

                    return new ModelAndView();
                } else {
                    return new ModelAndView("error-page/500");
                }
            }

        } catch (IOException e) {
            log.error("resolver IOException", e);
        }

        return null;
    }
}
