package com.api.canvas.student.dto.response.userSubjectGrade;

import java.math.BigDecimal;

public record GradesForSubjectResponse(
        String name,
        BigDecimal grade,
        BigDecimal weight
) {
}
