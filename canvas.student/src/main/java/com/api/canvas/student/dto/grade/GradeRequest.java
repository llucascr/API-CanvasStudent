package com.api.canvas.student.dto.grade;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequest {

    @NotBlank
    private BigDecimal grade;
    @NotBlank
    private BigDecimal weight;

}
