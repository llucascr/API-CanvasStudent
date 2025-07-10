package com.api.canvas.student.service;

import com.api.canvas.student.dto.SubjectDto;
import com.api.canvas.student.entities.Subject;
import com.api.canvas.student.exception.SubjectNotFound;
import com.api.canvas.student.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject createSubject(SubjectDto newSubject) {

        Subject subject = new Subject(
                null,
                newSubject.name(),
                newSubject.semester(),
                newSubject.status(),
                null
        );
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long subjectId) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        return subjectOptional.orElseThrow(() -> new SubjectNotFound("Materia com ID " + subjectId + "não encontrada"));
    }

    public void deleteSubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new SubjectNotFound("Materia com ID " + subjectId + "não encontrada");
        }
        subjectRepository.deleteById(subjectId);
    }

    public Subject updateSubject(Long subjectId, Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        if (subjectOptional.isPresent()) {
            subject.setSubjectId(subjectId);
            return subjectRepository.save(subject);
        }
        throw new SubjectNotFound("Materia com ID " + subjectId + "não encontrada");
    }

}
