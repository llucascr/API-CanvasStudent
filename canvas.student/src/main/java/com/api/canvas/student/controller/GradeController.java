package com.api.canvas.student.controller;

import com.api.canvas.student.dto.grade.GradeRequest;
import com.api.canvas.student.dto.grade.GradeResponse;
import com.api.canvas.student.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/grade")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping(path = "/create")
    public GradeResponse create(
            @RequestBody GradeRequest gradeRequest,
            @RequestParam Long userId,
            @RequestParam Long subjectId) {
        return gradeService.create(gradeRequest, userId, subjectId);
    }

}
