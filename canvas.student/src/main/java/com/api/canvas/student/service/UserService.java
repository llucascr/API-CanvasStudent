package com.api.canvas.student.service;

import com.api.canvas.student.dto.response.user.UserIdResponseDTO;
import com.api.canvas.student.dto.request.user.UserRequestDTO;
import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.exception.DataNotFoundException;
import com.api.canvas.student.mapper.UserMapper;
import com.api.canvas.student.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CanvasService canvasService;

    private final UserMapper mapper;

    public UserResponseDTO createNewUser(UserRequestDTO newUser) {
        UserIdResponseDTO userIdResponseDto = canvasService.getUserCanvasIdAndName(newUser.tokenCanvas());
        String email = canvasService.getUserCanvasEmail(newUser.tokenCanvas(), userIdResponseDto.id());

        User user = User.builder()
                .name(userIdResponseDto.name())
                .email(email)
                .password(newUser.password())
                .userCanvasId(userIdResponseDto.id())
                .tokenCanvas(newUser.tokenCanvas())
                .university(newUser.university())
                .course(newUser.course())
                .build();

        return mapper.fromUserToUserResponse(userRepository.save(user));
    }

    public PagedModel<UserResponseDTO> getAllUsers(Pageable pageable) {
        return mapper.fromPageToPagedModel(userRepository.findAll(pageable));
    }

    public UserResponseDTO getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("Usuário com ID " + userId + " não encontrado"));

        return mapper.fromUserToUserResponse(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataNotFoundException("Usuário com ID " + userId + " não encontrado");
        }

    }

    public User updateUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            user.setUserId(userId);
            return userRepository.save(user);
        }
        throw new DataNotFoundException("Usuário com ID " + userId + " não encontrado");
    }

}