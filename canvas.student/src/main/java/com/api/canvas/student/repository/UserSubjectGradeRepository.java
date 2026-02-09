package com.api.canvas.student.repository;

import com.api.canvas.student.entities.UserSubjectGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSubjectGradeRepository extends JpaRepository<UserSubjectGrade, Integer> {

    @Query("SELECT u FROM UserSubjectGrade u JOIN u.subject s WHERE  u.user.userId = :userId AND s.name = :subjectName")
    Optional<UserSubjectGrade> findByUserIdAndSubjectName(Long userId, String subjectName);

}
