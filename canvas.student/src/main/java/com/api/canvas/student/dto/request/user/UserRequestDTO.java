package com.api.canvas.student.dto.request.user;

public record UserRequestDTO(
        String tokenCanvas,
        String password,
        String university,
        String course
) {}