package com.api.canvas.student.dto;

import lombok.Data;

@Data
public class UserDto {

    private String tokenCanvas;
    private String password;
    private String university;
    private String course;

}
