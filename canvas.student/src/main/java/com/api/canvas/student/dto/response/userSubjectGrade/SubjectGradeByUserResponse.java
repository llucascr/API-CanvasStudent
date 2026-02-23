package com.api.canvas.student.dto.response.userSubjectGrade;

import java.math.BigDecimal;

public record SubjectGradeByUserResponse(
        String name,
        Byte semester,
        String description,
        BigDecimal grade,
        BigDecimal weight
){
}
