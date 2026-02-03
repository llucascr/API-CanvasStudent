package com.api.canvas.student.service;

import com.api.canvas.student.dto.response.user.UserIdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CanvasService {

    private static final Logger log = LoggerFactory.getLogger(CanvasService.class);

    private final RestTemplate restTemplate;

    public UserIdResponseDTO getUserCanvasIdAndName(String tokenCanvas) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenCanvas);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserIdResponseDTO> responseEntity = restTemplate.exchange(
                    "https://canvas.instructure.com/api/v1/users/self",
                    HttpMethod.GET,
                    entity,
                    UserIdResponseDTO.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("Canvas Id and Name: " + responseEntity.getBody());
                return responseEntity.getBody();
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String getUserCanvasEmail(String tokenCanvas, String userCanvasId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenCanvas);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    "https://canvas.instructure.com/api/v1/users/" + userCanvasId + "/profile",
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("Canvas email: " + responseEntity.getBody());
                return (String) responseEntity.getBody().get("primary_email").toString();
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
