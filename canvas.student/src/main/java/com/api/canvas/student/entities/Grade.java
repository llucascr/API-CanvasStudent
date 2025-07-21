package com.api.canvas.student.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "grade_tb")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    private BigDecimal grade;

    private BigDecimal weight;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    })
    private UserSubject userSubject;

}