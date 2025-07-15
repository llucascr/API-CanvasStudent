package com.api.canvas.student.exception;

import org.springframework.http.HttpStatus;

public class SubjectNotFound extends RuntimeException implements ApiExceptionInterface{

    private final String code = "SUBJECT_NOT_FOUND";
    private String message;

    public SubjectNotFound(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
