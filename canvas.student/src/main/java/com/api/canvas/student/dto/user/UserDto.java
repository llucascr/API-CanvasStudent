package com.api.canvas.student.dto.user;

public record UserDto(
        String tokenCanvas,
        String password,
        String university,
        String course
) {}