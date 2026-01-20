package com.api.canvas.student.dto.request.grade;


import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record GradeRequestDTO(
        @NotBlank BigDecimal grade,
        @NotBlank BigDecimal weight
) {}
