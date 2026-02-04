package com.api.canvas.student.login.controller.dto;

import com.api.canvas.student.login.entities.User;

public record UserResponseDTO(
        Long userId,
        String name,
        String email,
        String university,
        String course
) {

    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getUniversity(),
                user.getCourse()
        );
    }

}
