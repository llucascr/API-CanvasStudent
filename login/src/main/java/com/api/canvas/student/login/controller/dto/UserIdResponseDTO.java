package com.api.canvas.student.login.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserIdResponseDTO(
        String id,
        String name
) {}