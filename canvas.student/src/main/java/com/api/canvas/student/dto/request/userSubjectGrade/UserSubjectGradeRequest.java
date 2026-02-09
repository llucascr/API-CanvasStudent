package com.api.canvas.student.dto.request.userSubjectGrade;

import com.api.canvas.student.dto.request.subject.SubjectRequestDTO;

import java.math.BigDecimal;

public record UserSubjectGradeRequest(
        Long userId,
        SubjectRequestDTO subject,
        BigDecimal grade,
        BigDecimal weight,
        String description
) {
}
