package com.api.canvas.student.consumer;

import com.api.canvas.student.dto.response.user.UserResponseDTO;
import com.api.canvas.student.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginConsumer {

    private static final Logger log = LoggerFactory.getLogger(LoginConsumer.class);

    private final UserService userService;

    @RabbitListener(queues = "${broker.queue.login.name}")
    public void listenerLogin(UserResponseDTO dto) {
        log.info("Message from ms-login");
        userService.save(dto);
    }

}
