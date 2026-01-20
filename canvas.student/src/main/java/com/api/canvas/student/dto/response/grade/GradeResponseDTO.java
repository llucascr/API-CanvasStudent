package com.api.canvas.student.dto.response.grade;

import java.math.BigDecimal;

public record GradeResponseDTO(
        Long gradeId,
        BigDecimal grade,
        Long userId,
        Long subjectId
) {}
