package com.api.canvas.student.dto.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserIdResponseDTO(
        String id,
        String name
) {}