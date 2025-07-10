package com.api.canvas.student.service;

import com.api.canvas.student.dto.UserDto;
import com.api.canvas.student.dto.UserIdDto;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.exception.UserNotFound;
import com.api.canvas.student.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public User createNewUser(UserDto newUser) {
        UserIdDto userIdDto = getUserCanvasIdAndName(newUser.tokenCanvas());
        String email = getUserCanvasEmail(newUser.tokenCanvas(), userIdDto.getId());

        User user = new User(
                null,
                userIdDto.getName(),
                email,
                newUser.password(),
                userIdDto.getId(),
                newUser.tokenCanvas(),
                newUser.university(),
                newUser.course(),
                null
        );
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new UserNotFound("Usuário com ID " + userId + "não encontrado"));
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound("Usuário com ID " + userId + "não encontrado");
        }
        userRepository.deleteById(userId);
    }

    public User updateUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            user.setUserId(userId);
            return userRepository.save(user);
        }
        throw new UserNotFound("Usuário com ID " + userId + "não encontrado");
    }

}
