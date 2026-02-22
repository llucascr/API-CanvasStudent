package com.api.canvas.student.repository;

import com.api.canvas.student.dto.response.userSubjectGrade.SubjectGradeByUserResponse;
import com.api.canvas.student.dto.response.userSubjectGrade.UserSubjectGradeResponse;
import com.api.canvas.student.entities.UserSubjectGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSubjectGradeRepository extends JpaRepository<UserSubjectGrade, Integer> {

    @Query("SELECT u FROM UserSubjectGrade u JOIN u.subject s WHERE  u.user.userId = :userId AND s.name = :subjectName")
    Optional<UserSubjectGrade> findByUserIdAndSubjectName(Long userId, String subjectName);

    @Query(value = """
        SELECT 
            s.name AS materia,
            usg.description,
            usg.grade,
            usg.weight
        FROM tb_user_subject_grade usg
        INNER JOIN tb_subject s ON usg.subject_id = s.subject_id
        WHERE usg.user_id = :userId
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM tb_user_subject_grade usg
        WHERE usg.user_id = :userId
        """,
            nativeQuery = true)
    Optional<Page<SubjectGradeByUserResponse>> findUserSubjectGradeByUserId(@Param("userId") Long userId, Pageable pageable);

}
