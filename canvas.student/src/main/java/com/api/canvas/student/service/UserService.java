package com.api.canvas.student.service;

import com.api.canvas.student.dto.UserDto;
import com.api.canvas.student.dto.UserIdDto;
import com.api.canvas.student.entities.User;
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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private UserIdDto getUserCanvasIdAndName(String tokenCanvas) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://canvas.instructure.com/api/v1/users/self"))
                    .header("Authorization", "Bearer " + tokenCanvas)
                    .GET()
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            String responseBody = response.body().toString();
            return mapper.readValue(responseBody, UserIdDto.class);

        } catch (EntityNotFoundException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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
        UserIdDto userIdDto = getUserCanvasIdAndName(newUser.getTokenCanvas());
        String email = getUserCanvasEmail(newUser.getTokenCanvas(), userIdDto.getId());

        User user = new User(
                null,
                userIdDto.getName(),
                email,
                newUser.getPassword(),
                userIdDto.getId(),
                newUser.getTokenCanvas(),
                newUser.getUniversity(),
                newUser.getCourse(),
                null
        );
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*public User getUserById(){}*/

}
