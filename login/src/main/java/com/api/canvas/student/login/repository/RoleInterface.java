package com.api.canvas.student.login.repository;

import com.api.canvas.student.login.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleInterface extends JpaRepository<Role, Long> {
}
