package com.api.canvas.student.login.config;

import com.api.canvas.student.login.entities.Role;
import com.api.canvas.student.login.entities.User;
import com.api.canvas.student.login.repository.RoleRepository;
import com.api.canvas.student.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminUserConfig.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.getDescription());
        Optional<User> userAdmin = userRepository.findByEmail("admin@email.com");

        userAdmin.ifPresentOrElse(
                user -> {
                    log.info("Admin already exist");
                },
                () -> {
                    userRepository.save(User.builder()
                            .name("admin")
                            .email("admin@email.com")
                            .password(passwordEncoder.encode("admin"))
                            .userCanvasId("")
                            .tokenCanvas("")
                            .university("")
                            .course("")
                            .roles(Set.of(roleAdmin))
                            .build()
                    );
                }
        );

    }

}
