package com.api.canvas.student.service;

import com.api.canvas.student.dto.UserIdDto;
import com.api.canvas.student.entities.User;
import com.api.canvas.student.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*public UserIdDto getUserCanvasId(Long userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()){
                User user = optionalUser.get();

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://canvas.instructure.com/api/v1/users/self"))
                        .header("Authorization", "Bearer " + user.getTokenCanvas())
                        .GET()
                        .build();
                HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String responseBody = response.body().toString();
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(responseBody, UserIdDto.class);
                } else {
                    throw new EntityNotFoundException("User com ID " + userId + " não encontrado");
                }
            }
        } catch (EntityNotFoundException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public UserIdDto getUserCanvasIdAndName(String tokenCanvas) {
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

}
