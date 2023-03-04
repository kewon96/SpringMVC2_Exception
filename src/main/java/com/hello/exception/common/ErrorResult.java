package com.hello.exception.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResult {
    private Class<?> exception;

    private String message;

    public ErrorResult(Exception ex) {
        this.exception = ex.getClass();
        this.message = ex.getMessage();
    }

    public String convertToJson(ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
