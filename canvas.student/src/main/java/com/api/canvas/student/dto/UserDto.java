package com.api.canvas.student.dto;

public record UserDto(
        String tokenCanvas,
        String password,
        String university,
        String course
) {}
