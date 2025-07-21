package com.api.canvas.student.exception;

import org.springframework.http.HttpStatus;

public interface ApiExceptionInterface {
    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}