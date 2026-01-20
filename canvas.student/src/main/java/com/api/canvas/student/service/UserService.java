package com.api.canvas.student.service;

import com.api.canvas.student.dto.response.user.UserIdResponseDTO;
import com.api.canvas.student.dto.request.user.UserRequestDTO;
import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.exception.UserNotFound;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;
    private final WebClient canvasWebClient;

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public UserIdResponseDTO getUserCanvasIdAndName(String tokenCanvas) throws EntityNotFoundException {
        return canvasWebClient.get()
                .uri("/api/v1/users/self")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenCanvas)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new Exception("Autenticação falhou")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new Exception("Servidor indisponível")))
                .bodyToMono(UserIdResponseDTO.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    private String getUserCanvasEmail(String tokenCanvas, String userCanvasId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://canvas.instructure.com/api/v1/users/" + userCanvasId + "/profile"))
                    .header("Authorization", "Bearer " + tokenCanvas)
                    .GET()
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body().toString();
                ObjectMapper mapper = new ObjectMapper();

                JsonNode root = mapper.readTree(responseBody);
                return root.get("primary_email").asText();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserResponseDTO createNewUser(UserRequestDTO newUser) {
        UserIdResponseDTO userIdResponseDto = getUserCanvasIdAndName(newUser.tokenCanvas());
        String email = getUserCanvasEmail(newUser.tokenCanvas(), userIdResponseDto.id());

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
                () -> new UserNotFound("Usuário com ID " + userId + " não encontrado"));

        return mapper.fromUserToUserResponse(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound("Usuário com ID " + userId + " não encontrado");
        }

    }

    public User updateUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            user.setUserId(userId);
            return userRepository.save(user);
        }
        throw new UserNotFound("Usuário com ID " + userId + " não encontrado");
    }

}