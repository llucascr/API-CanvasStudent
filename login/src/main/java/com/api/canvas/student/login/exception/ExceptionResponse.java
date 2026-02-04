package com.api.canvas.student.login.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(
        LocalDateTime timestamp,
        String message,
        String details
) { }
