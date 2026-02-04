package com.api.canvas.student.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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
    @JsonIgnore
    private List<UserSubject> users;

}