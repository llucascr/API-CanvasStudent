package com.api.canvas.student.service;

import com.api.canvas.student.dto.request.subject.SubjectRequestDTO;
import com.api.canvas.student.dto.request.userSubjectGrade.UserSubjectGradeRequest;
import com.api.canvas.student.dto.response.subject.UserSubjectResponse;
import com.api.canvas.student.entities.*;
import com.api.canvas.student.exception.DataNotFoundException;
import com.api.canvas.student.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject createSubject(SubjectRequestDTO newSubject) {

        Subject subject = Subject.builder()
                .name(newSubject.name())
                .semester(newSubject.semester())
                .status(StatusSubject.CURSANDO)
                .build();

        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(Long subjectId) {
        return subjectRepository.findById(subjectId);
    }

    public void deleteSubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new DataNotFoundException("Materia com ID " + subjectId + " não encontrada");
        }
        subjectRepository.deleteById(subjectId);
    }

    public Subject updateSubject(Long subjectId, Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        if (subjectOptional.isPresent()) {
            subject.setSubjectId(subjectId);
            return subjectRepository.save(subject);
        }
        throw new DataNotFoundException("Materia com ID " + subjectId + " não encontrada");
    }

}