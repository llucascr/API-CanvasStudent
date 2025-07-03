package com.api.canvas.student.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject_tb")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private byte semester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSubject status;

    @OneToMany(mappedBy = "subject")
    private List<UserSubject> users;

}
