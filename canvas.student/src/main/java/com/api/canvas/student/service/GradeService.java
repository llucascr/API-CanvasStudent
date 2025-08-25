package com.api.canvas.student.service;

import com.api.canvas.student.dto.grade.GradeRequest;
import com.api.canvas.student.dto.grade.GradeResponse;
import com.api.canvas.student.entities.Grade;
import com.api.canvas.student.entities.Subject;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.entities.UserSubject;
import com.api.canvas.student.repository.GradeRepository;
import com.api.canvas.student.repository.SubjectRepository;
import com.api.canvas.student.repository.UserRepository;
import com.api.canvas.student.repository.UserSubjectRespository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    private final UserSubjectRespository  userSubjectRespository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public GradeResponse create(GradeRequest request, Long userId, Long subjectId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        UserSubject userSubject = (UserSubject) userSubjectRespository.findByUserAndSubject(user, subject)
                .orElseGet(() -> {
                    UserSubject newUserSubject = UserSubject.builder()
                            .user(user)
                            .subject(subject)
                            .finalGrade(BigDecimal.ZERO)
                            .build();
                    return userSubjectRespository.save(newUserSubject);
                });

        Grade grade = Grade.builder()
                .grade(request.getGrade())
                .weight(request.getWeight())
                .userSubject(userSubject)
                .build();

        userSubject.getGrades().add(grade);
        Grade savedGrade = gradeRepository.save(grade);

        return modelMapper.map(savedGrade, GradeResponse.class);
    }


}
