package com.api.canvas.student.service;

import com.api.canvas.student.dto.SubjectDto;
import com.api.canvas.student.entities.StatusSubject;
import com.api.canvas.student.entities.Subject;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.exception.SubjectNotFound;
import com.api.canvas.student.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SubjectService {


    private final SubjectRepository subjectRepository;

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

}