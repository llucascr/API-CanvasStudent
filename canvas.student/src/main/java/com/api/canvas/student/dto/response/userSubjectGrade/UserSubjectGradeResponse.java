package com.api.canvas.student.dto.response.userSubjectGrade;

import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.Subject;

import java.math.BigDecimal;

public record UserSubjectGradeResponse(
        UserResponseDTO user,
        Subject subject,
        BigDecimal grade,
        BigDecimal weight,
        String description
) {
}
