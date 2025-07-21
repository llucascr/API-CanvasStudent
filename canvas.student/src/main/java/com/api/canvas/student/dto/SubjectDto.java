package com.api.canvas.student.dto;

import com.api.canvas.student.entities.StatusSubject;

public record SubjectDto(
        String name,
        byte semester,
        StatusSubject status
) {}