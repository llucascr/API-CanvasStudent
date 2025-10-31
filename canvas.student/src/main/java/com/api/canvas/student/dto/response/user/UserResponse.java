package com.api.canvas.student.dto.response.user;

import com.api.canvas.student.entities.UserSubject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    Long userId;
    String name;
    String email;
    String university;
    String course;
}
