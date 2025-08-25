package com.api.canvas.student.dto.grade;

import com.api.canvas.student.entities.UserSubject;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponse {

    private Long gradeId;
    private BigDecimal grade;
    private Long userId;
    private Long subjectId;

}
