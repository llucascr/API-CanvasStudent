package com.api.canvas.student.repository;

import com.api.canvas.student.entities.Subject;
import com.api.canvas.student.entities.replica.User;
import com.api.canvas.student.entities.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSubjectRespository extends JpaRepository<UserSubject, Long> {
    List<UserSubject> findByUser(User user);

    <T> Optional<T> findByUserAndSubject(User user, Subject subject);
}
