package com.api.canvas.student.login.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    public enum Values {
        STUDENT(1L, "student"),
        TEACHER(2L, "teacher"),
        ADMIN(3L, "admin");

        @Getter
        private Long id;
        @Getter
        private String description;

        Values(Long id, String description) {
            this.id = id;
            this.description = description;
        }

    }

}
