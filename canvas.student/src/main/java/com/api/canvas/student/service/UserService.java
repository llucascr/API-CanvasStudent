package com.api.canvas.student.service;

import com.api.canvas.student.dto.request.user.UserDto;
import com.api.canvas.student.dto.UserIdDto;
import com.api.canvas.student.dto.request.user.UserRequest;
import com.api.canvas.student.dto.response.user.UserResponse;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.exception.UserNotFound;
import com.api.canvas.student.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserIdDto getUserCanvasIdAndName(String tokenCanvas) throws EntityNotFoundException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://canvas.instructure.com/api/v1/users/self"))
                    .header("Authorization", "Bearer " + tokenCanvas)
                    .GET()
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                String responseBody = response.body().toString();
                return mapper.readValue(responseBody, UserIdDto.class);
            }
            return null;

        } catch (EntityNotFoundException | IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
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

    public UserResponse createNewUser(UserRequest newUser) {
        UserIdDto userIdDto = getUserCanvasIdAndName(newUser.getTokenCanvas());
        String email = getUserCanvasEmail(newUser.getTokenCanvas(), userIdDto.getId());

        User user = User.builder()
                .name(userIdDto.getName())
                .email(email)
                .password(newUser.getPassword())
                .userCanvasId(userIdDto.getId())
                .tokenCanvas(newUser.getTokenCanvas())
                .university(newUser.getUniversity())
                .course(newUser.getCourse())
                .build();

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);

        List<UserResponse> userResponses = users.getContent().stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();

        return new PageImpl<>(userResponses, pageable, users.getTotalElements());
    }

    public User getUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new UserNotFound("Usuário com ID " + userId + " não encontrado"));
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound("Usuário com ID " + userId + " não encontrado");
        }
        userRepository.deleteById(userId);
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