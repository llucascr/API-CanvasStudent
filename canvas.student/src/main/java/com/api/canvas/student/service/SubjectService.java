package com.api.canvas.student.service;

import com.api.canvas.student.dto.subject.SubjectDto;
import com.api.canvas.student.dto.subject.UserSubjectResponse;
import com.api.canvas.student.entities.*;
import com.api.canvas.student.exception.SubjectNotFound;
import com.api.canvas.student.exception.UserNotFound;
import com.api.canvas.student.repository.SubjectRepository;
import com.api.canvas.student.repository.UserRepository;
import com.api.canvas.student.repository.UserSubjectRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubjectService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final UserSubjectRespository  userSubjectRespository;
    private final ModelMapper modelMapper;

    public Subject createSubject(SubjectDto newSubject) {

        Subject subject = Subject.builder()
                .name(newSubject.name())
                .semester(newSubject.semester())
                .status(StatusSubject.CURSANDO)
                .users(new ArrayList<>())
                .build();

        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long subjectId) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        return subjectOptional.orElseThrow(() -> new SubjectNotFound("Materia com ID " + subjectId + " não encontrada"));
    }

    public void deleteSubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new SubjectNotFound("Materia com ID " + subjectId + " não encontrada");
        }
        subjectRepository.deleteById(subjectId);
    }

    public Subject updateSubject(Long subjectId, Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        if (subjectOptional.isPresent()) {
            subject.setSubjectId(subjectId);
            return subjectRepository.save(subject);
        }
        throw new SubjectNotFound("Materia com ID " + subjectId + " não encontrada");
    }

    public List<UserSubjectResponse> addUserToSubject(Long subjectId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("Usuário com ID " + userId + " não encontrado"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFound("Matéria com ID " + subjectId + " não encontrada"));

        UserSubject userSubject = UserSubject.builder()
                .userSubjectId(new UserSubjectId(user.getUserId(), subject.getSubjectId()))
                .user(user)
                .subject(subject)
                .finalGrade(BigDecimal.valueOf(0))
                .build();

        userSubjectRespository.save(userSubject);

        List<UserSubject> allSubjects = userSubjectRespository.findByUser(user);


        return allSubjects.stream()
                .map(us -> new UserSubjectResponse(
                        us.getSubject().getSubjectId(),
                        us.getSubject().getName(),
                        us.getSubject().getSemester(),
                        us.getSubject().getStatus(),
                        us.getFinalGrade()
                ))
                .toList();
    }


}