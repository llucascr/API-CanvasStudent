package com.api.canvas.student.login.controller.dto;

public record UserRequestDTO(
        String tokenCanvas,
        String password,
        String university,
        String course
) {}