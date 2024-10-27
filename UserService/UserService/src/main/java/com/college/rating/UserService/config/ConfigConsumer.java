package com.college.rating.UserService.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Component
public class ConfigConsumer {
    // Getter methods
    @Value("${user.role}")
    private String userRole;

    @Value("${welcome.message}")
    private String welcomeMessage;

}
