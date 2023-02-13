package com.hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    // RequestDispatcher 상수로 정의되어 있음

    // 예외
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";

    // 예외 타입
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    // Error Message
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";

    // 요청했던 URL
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";

    // 예외가 발생한 Servlet Name
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";

    // HTTP Status Code
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("ERROR 404!!!");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("ERROR 500!!!");
        printErrorInfo(request);
        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response) {
        log.info("ERROR 500!!!");

        Map<String, Object> result = new HashMap<>();

        // 요청에 담겨진 Exception객체를 가져온다.
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);

        // 에러 상태코드 주입
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));

        // 메세지 주입
        result.put("message", ex.getMessage());

        // HTTP 상태코드 주입
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // 응답객체에 그대로 주입
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex={}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE)); // ex의 경우 NestedServletException 스프링이 한번 감싸서 반환
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());

    }

}
