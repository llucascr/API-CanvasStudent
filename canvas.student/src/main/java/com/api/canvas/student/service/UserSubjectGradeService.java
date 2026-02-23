package com.api.canvas.student.service;

import com.api.canvas.student.dto.request.userSubjectGrade.UserSubjectGradeRequest;
import com.api.canvas.student.dto.response.userSubjectGrade.SubjectGradeByUserResponse;
import com.api.canvas.student.dto.response.userSubjectGrade.UserSubjectGradeResponse;
import com.api.canvas.student.entities.UserSubjectGrade;
import com.api.canvas.student.exception.DataAlreadyExistException;
import com.api.canvas.student.exception.DataNotFoundException;
import com.api.canvas.student.repository.UserSubjectGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSubjectGradeService {

    private final UserSubjectGradeRepository userSubjectGradeRepository;

    private final UserService userService;
    private final SubjectService subjectService;

    public UserSubjectGradeResponse create(UserSubjectGradeRequest dto) {

        Optional<UserSubjectGrade> userSubjectGrade = userSubjectGradeRepository
                .findByUserIdAndSubjectName(dto.userId(), dto.subject().name());

        if (userSubjectGrade.isPresent()) {
            return userSubjectGradeRepository.save(UserSubjectGrade.builder()
                    .user(userSubjectGrade.get().getUser())
                    .subject(userSubjectGrade.get().getSubject())
                    .grade(dto.grade())
                    .weight(dto.weight())
                    .description(dto.description())
                    .build()).toResponse();
        }

        return userSubjectGradeRepository.save(UserSubjectGrade.builder()
                .user(userService.findById(dto.userId()))
                .subject(subjectService.createSubject(dto.subject()))
                .grade(dto.grade())
                .weight(dto.weight())
                .description(dto.description())
                .build()).toResponse();
    }

    public PagedModel<SubjectGradeByUserResponse> findAllByUserId(Long userId, Pageable pageable) {
        Page<SubjectGradeByUserResponse> subjectForUser = userSubjectGradeRepository.findUserSubjectGradeByUserId(userId, pageable)
                .orElseThrow(() -> new DataNotFoundException("Nenhuma matéria encontrada"));

        return new PagedModel<>(subjectForUser);
    }

}
