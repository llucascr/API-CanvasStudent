package com.api.canvas.student.dto.response.user;

import com.api.canvas.student.entities.enums.ActionMessage;
import com.api.canvas.student.entities.replica.Role;

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
) {}
