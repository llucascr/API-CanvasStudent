package com.api.canvas.student.dto.response.user;

import com.api.canvas.student.entities.enums.ActionMessage;
import com.api.canvas.student.entities.replica.Role;
import com.api.canvas.student.entities.replica.User;

import java.util.ArrayList;
import java.util.Set;

public record UserResponseDTO(
        Long userId,
        String name,
        String email,
        String userCanvasId,
        String tokenCanvas,
        String university,
        String course,
        Set<Role> roles,
        ActionMessage action
) {

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .userCanvasId(userCanvasId)
                .tokenCanvas(tokenCanvas)
                .university(university)
                .course(course)
                .subjects(new ArrayList<>())
                .roles(roles)
                .build();
    }

}
