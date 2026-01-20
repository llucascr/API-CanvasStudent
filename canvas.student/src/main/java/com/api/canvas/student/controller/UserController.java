package com.api.canvas.student.controller;

import com.api.canvas.student.dto.request.user.UserRequestDTO;
import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

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

    @GetMapping("/verifyToken")
    public ResponseEntity<?> getUserCanvasId(@RequestParam String tokenCanvas) {
        if (userService.getUserCanvasIdAndName(tokenCanvas) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token Invalido");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Token Valido");
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createNewUser(@RequestBody UserRequestDTO newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(newUser));
    }

    @GetMapping("/all")
    public ResponseEntity<PagedModel<UserResponseDTO>> getAllUser(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(pageable));
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

}