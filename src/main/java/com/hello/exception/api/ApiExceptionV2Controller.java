package com.hello.exception.api;

import com.hello.exception.common.ErrorResult;
import com.hello.exception.exception.UserException;
import com.hello.exception.exception.UserExceptionChildException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api2")
public class ApiExceptionV2Controller {

    /**
     * <pre>
     * 여기서 IllegalArgumentException을 catch한다.
     * ErrorResult는 별도처리할 필요없이 JSON으로 변환된다.
     *
     * 하지만 이때 여기서 정상흐름으로 바꿔버리기때문에 200으로 응답코드가 넘어가게 된다.
     * 물론 결과적으로 보면 맞긴하지만 내가 원하는 결과는 400이 주입됐기를 원했다.
     * 그래서 ResponseStatus로 주입시켜주면 원하는대로 코드가 주입된다.
     * </pre>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    /**
     * 구현할 때 질리듯이 구현한 Controller를 구현하듯이 한다고 생각하면 마음이 편하다
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);

        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * 여기서 처리할 예외를 지정하면 그 예외만 커버하는게 아니라 그 예외를 상속받은 자식예외도 처리한다.
     *
     * 즉, 여기처럼 Exception의 경우 위에 있는 예외를 처리하지못할 때 최종적으로 도달하게 되는 Handler이다.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exHandler(Exception e) {
        log.error("[exception] ex", e);
        return new ErrorResult("EX", "내부 에러");
    }

    @GetMapping("/member/{id}")
    public MemberDto getMember(@PathVariable("id") String id) throws Exception {
        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자!");
        }

        // 예를들어 "bad"라는 문자열이 잘못된 값이라고 해보자...
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if(id.contains("user-ex")) {
            throw new UserExceptionChildException("Error User!");
        }

        if(id.contains("exception")) {
            throw new Exception("예외발생");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
