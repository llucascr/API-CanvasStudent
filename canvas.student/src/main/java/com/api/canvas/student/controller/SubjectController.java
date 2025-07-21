package com.api.canvas.student.controller;

import com.api.canvas.student.dto.SubjectDto;
import com.api.canvas.student.entities.Subject;
import com.api.canvas.student.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.createSubject(subjectDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping
    public ResponseEntity<?> getSubjectById(@RequestParam Long subjectId) {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getSubjectById(subjectId));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSubject(@RequestParam Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateSubject(@RequestBody Subject subject) {
        return ResponseEntity.ok(subjectService.updateSubject(subject.getSubjectId(), subject));
    }

}