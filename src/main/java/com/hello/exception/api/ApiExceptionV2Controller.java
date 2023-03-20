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
