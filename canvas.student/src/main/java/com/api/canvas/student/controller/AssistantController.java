package com.api.canvas.student.controller;

import com.api.canvas.student.service.UserService;
import com.api.canvas.student.service.interfaces.AssistantAiService;
import com.api.canvas.student.service.tools.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/assistant")
public class AssistantController {

    private final AssistantAiService assistantAiService;
    private final UserContext userContext;

    @PostMapping
    public String askAssistant(@RequestParam Long userId, @RequestBody String userMessage) {
        userContext.setUserId(userId);
       return assistantAiService.calculateGrade(userMessage);
    }

}
