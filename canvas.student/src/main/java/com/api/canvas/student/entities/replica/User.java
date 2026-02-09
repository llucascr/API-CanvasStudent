package com.api.canvas.student.entities.replica;

import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.Subject;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "replica_tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public UserResponseDTO toResponseDTO() {
        return new UserResponseDTO(
                this.userId,
                this.name,
                this.email,
                this.userCanvasId,
                this.tokenCanvas,
                this.university,
                this.course,
                this.roles
        );
    }

}