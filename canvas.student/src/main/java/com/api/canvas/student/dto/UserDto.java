package com.api.canvas.student.dto;

import lombok.Data;

public record UserDto(
        String tokenCanvas,
        String password,
        String university,
        String course
) {}
