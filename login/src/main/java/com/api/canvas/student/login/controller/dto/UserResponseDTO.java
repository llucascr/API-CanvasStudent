package com.api.canvas.student.login.controller.dto;

import com.api.canvas.student.login.entities.Role;
import com.api.canvas.student.login.entities.User;

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
