package com.api.canvas.student.repository;

import com.api.canvas.student.entities.User;
import com.api.canvas.student.entities.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubjectRespository extends JpaRepository<UserSubject, Long> {
    List<UserSubject> findByUser(User user);
}
