package com.api.canvas.student.dto.subject;

import com.api.canvas.student.entities.StatusSubject;

import java.math.BigDecimal;

public record UserSubjectResponse(Long subjectId, String subjectName, int semester, StatusSubject status, BigDecimal finalGrade) {}

