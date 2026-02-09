package com.api.canvas.student.service;

import com.api.canvas.student.dto.request.userSubjectGrade.UserSubjectGradeRequest;
import com.api.canvas.student.dto.response.userSubjectGrade.UserSubjectGradeResponse;
import com.api.canvas.student.entities.UserSubjectGrade;
import com.api.canvas.student.exception.DataAlreadyExistException;
import com.api.canvas.student.repository.UserSubjectGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSubjectGradeService {

    private final UserSubjectGradeRepository userSubjectGradeRepository;

    private final UserService userService;
    private final SubjectService subjectService;

    public UserSubjectGradeResponse create(UserSubjectGradeRequest dto) {

        if (userSubjectGradeRepository.findByUserIdAndSubjectName(dto.userId(), dto.subject().name()).isPresent()) {
            throw new DataAlreadyExistException("Matéria já existe");
        }

        UserSubjectGrade userSubjectGrade = UserSubjectGrade.builder()
                .user(userService.findById(dto.userId()))
                .subject(subjectService.createSubject(dto.subject()))
                .grade(dto.grade())
                .weight(dto.weight())
                .description(dto.description())
                .build();

        return userSubjectGradeRepository.save(userSubjectGrade).toResponse();
    }

}
