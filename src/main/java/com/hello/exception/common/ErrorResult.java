package com.hello.exception.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ErrorResult {
    private Class<?> exception;

    private String code;

    private String message;

    public ErrorResult(Exception ex) {
        this.exception = ex.getClass();
        this.message = ex.getMessage();
    }

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String convertToJson(ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
