package com.api.canvas.student.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userSubject_tb")
public class UserSubject {

    @EmbeddedId
    private UserSubjectId userSubjectId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "final_grade")
    private BigDecimal finalGrade;

    @OneToMany(mappedBy = "userSubject", cascade = CascadeType.ALL)
    private List<Grade> grades = new ArrayList<>();
}
