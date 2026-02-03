package com.api.canvas.student.login.repository;

import com.api.canvas.student.login.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
