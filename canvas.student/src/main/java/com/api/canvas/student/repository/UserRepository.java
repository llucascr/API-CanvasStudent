package com.api.canvas.student.repository;

import com.api.canvas.student.entities.replica.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
