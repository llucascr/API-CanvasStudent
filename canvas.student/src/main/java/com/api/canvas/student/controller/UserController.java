package com.api.canvas.student.controller;

import com.api.canvas.student.dto.UserDto;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*@GetMapping("{tokenCanvas}")
    public ResponseEntity<?> getUserCanvasId(@PathVariable String tokenCanvas) {
        try {
            return ResponseEntity.ok(userService.getUserCanvasIdAndName(tokenCanvas));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("{tokenCanvas}/{userCanvasId}")
    public ResponseEntity<?> getUserCanvasEmail(@PathVariable String tokenCanvas, @PathVariable String userCanvasId) {
        try {
            return ResponseEntity.ok(userService.getUserCanvasEmail(tokenCanvas, userCanvasId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody UserDto newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(newUser));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
