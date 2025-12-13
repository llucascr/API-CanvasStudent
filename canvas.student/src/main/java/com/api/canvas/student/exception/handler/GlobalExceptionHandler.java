package com.api.canvas.student.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorResponse> handlerApiException(ApiExceptionInterface contract) {
//        HttpStatus status = contract.getHttpStatus();
//        ErrorResponse error = new ErrorResponse(
//                contract.getCode(),
//                contract.getMessage(),
//                status.value()
//        );
//        return ResponseEntity.status(status).body(error);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handlerGenericException(Exception ex) {
//        ErrorResponse error = new ErrorResponse(
//                "INTERNAL_SERVER_ERROR",
//                "Ocorreu um erro inesperado",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }

}