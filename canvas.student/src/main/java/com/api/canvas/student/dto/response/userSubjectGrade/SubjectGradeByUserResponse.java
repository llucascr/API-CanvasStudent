package com.api.canvas.student.dto.response.userSubjectGrade;

import java.math.BigDecimal;

public record SubjectGradeByUserResponse(
        String name,
        String description,
        BigDecimal grade,
        BigDecimal weight
){
}
