package com.api.canvas.student.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userCanvasId;

    @Column(name = "token_canvas",nullable = false)
    private String tokenCanvas;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String course;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserSubject> subjects;

}