package com.api.canvas.student.dto.response.user;

public record UserResponseDTO(
        Long userId,
        String name,
        String email,
        String university,
        String course
) {}
