package com.api.canvas.student.controller;

import com.api.canvas.student.dto.request.grade.GradeRequestDTO;
import com.api.canvas.student.dto.response.grade.GradeResponseDTO;
import com.api.canvas.student.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/grade")
public class GradeController {

    private final GradeService gradeService;

//    @PostMapping(path = "/create")
//    public GradeResponseDTO create(
//            @RequestBody GradeRequestDTO gradeRequest,
//            @RequestParam Long userId,
//            @RequestParam Long subjectId) {
//        return gradeService.create(gradeRequest, userId, subjectId);
//    }

}
