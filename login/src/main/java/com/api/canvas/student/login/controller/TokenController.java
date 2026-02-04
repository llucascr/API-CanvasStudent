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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CanvasService canvasService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid");
        }

        Instant now = Instant.now();
        long expiresIn = 300L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("API-CanvasStudent")
                .subject(user.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
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

        return ResponseEntity.ok(
                new UserResponseDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getUniversity(),
                    user.getCourse()
                )
        );
    }

}
