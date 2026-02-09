package com.api.canvas.student.service;

import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.entities.replica.User;
import com.api.canvas.student.exception.DataNotFoundException;
import com.api.canvas.student.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public void save(UserResponseDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            log.info("User with email {} already registered in the replica database", dto.email());
            return;
        }

        userRepository.save(dto.toEntity());

    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Usuário com ID " + userId + " não encontrado"));
    }

}
