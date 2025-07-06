package com.api.canvas.student.service;

import com.api.canvas.student.dto.SubjectDto;
import com.api.canvas.student.entities.Subject;
import com.api.canvas.student.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

}
