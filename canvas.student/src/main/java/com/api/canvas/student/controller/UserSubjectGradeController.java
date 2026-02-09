package com.api.canvas.student.controller;

import com.api.canvas.student.dto.request.userSubjectGrade.UserSubjectGradeRequest;
import com.api.canvas.student.dto.response.userSubjectGrade.UserSubjectGradeResponse;
import com.api.canvas.student.service.UserSubjectGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userSubjectGrade")
public class UserSubjectGradeController {

    private final UserSubjectGradeService userSubjectGradeService;

    @PostMapping
    public ResponseEntity<UserSubjectGradeResponse> create(@RequestBody UserSubjectGradeRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userSubjectGradeService.create(dto));
    }

}
