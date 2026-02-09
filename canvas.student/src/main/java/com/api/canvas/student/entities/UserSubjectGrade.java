package com.api.canvas.student.entities;

import com.api.canvas.student.dto.response.userSubjectGrade.UserSubjectGradeResponse;
import com.api.canvas.student.entities.replica.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_user_subject_grade")
public class UserSubjectGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_subject_grade_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(precision = 4, scale = 2)
    private BigDecimal grade;

    private BigDecimal weight;

    private String description;

    public UserSubjectGradeResponse toResponse() {
        return new UserSubjectGradeResponse(
                this.user.toResponseDTO(),
                this.subject,
                this.grade,
                this.weight,
                this.description
        );
    }

}
