package com.api.canvas.student.login.controller;

import com.api.canvas.student.login.controller.dto.*;
import com.api.canvas.student.login.entities.Role;
import com.api.canvas.student.login.entities.User;
import com.api.canvas.student.login.exception.DataAlreadyExistException;
import com.api.canvas.student.login.repository.RoleRepository;
import com.api.canvas.student.login.repository.UserRepository;
import com.api.canvas.student.login.service.CanvasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final CanvasService canvasService;

    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    private final RabbitTemplate rabbitTemplate;
    @Value("${broker.queue.login.name}")
    private String routingKey;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid");
        }

        Instant now = Instant.now();
        long expiresIn = 86400000L; // 24 horas

        String scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("API-CanvasStudent")
                .subject(user.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));

    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {

        UserIdResponseDTO userIdResponseDto = canvasService.getUserCanvasIdAndName(dto.tokenCanvas());
        String email = canvasService.getUserCanvasEmail(dto.tokenCanvas(), userIdResponseDto.id());

        if (userRepository.existsByEmail(email)) {
            throw new DataAlreadyExistException("Email invalid");
        }

        Role basicRole = roleRepository.findByName(Role.Values.STUDENT.getDescription());
        userRepository.findByEmail(email);

        User user = User.builder()
                .name(userIdResponseDto.name())
                .email(email)
                .password(passwordEncoder.encode(dto.password()))
                .userCanvasId(userIdResponseDto.id())
                .tokenCanvas(dto.tokenCanvas())
                .university(dto.university())
                .course(dto.course())
                .roles(Set.of(basicRole))
                .build();

        userRepository.save(user);
        UserResponseDTO userResponseDTO = user.toUserResponseDTO();

        rabbitTemplate.convertAndSend("", routingKey, userResponseDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<PagedModel<User>> listUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(userRepository.findAll(pageable)));
    }

}
