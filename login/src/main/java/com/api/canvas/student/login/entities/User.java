package com.api.canvas.student.login.entities;

import com.api.canvas.student.login.controller.dto.ActionMessage;
import com.api.canvas.student.login.controller.dto.LoginRequest;
import com.api.canvas.student.login.controller.dto.UserResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

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
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public UserResponseDTO toUserResponseDTO() {
        return new UserResponseDTO(
                this.getUserId(),
                this.getName(),
                this.getEmail(),
                this.getUserCanvasId(),
                this.getTokenCanvas(),
                this.getUniversity(),
                this.getCourse(),
                this.getRoles()
        );
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
