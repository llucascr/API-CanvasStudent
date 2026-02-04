package com.api.canvas.student.entities.replica;

import com.api.canvas.student.entities.UserSubject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "replica_tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "user_canvas_id", length = 500, nullable = false, unique = true)
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}