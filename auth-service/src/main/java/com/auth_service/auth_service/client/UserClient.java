package com.auth_service.auth_service.client;

import com.auth_service.auth_service.dto.LoginRequest;
import com.auth_service.auth_service.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class UserClient {

    private final WebClient webClient;
    private final String userServiceUrl;

    public UserClient(WebClient webClient,
                      @Value("${user.service.url}") String userServiceUrl) {
        this.webClient = webClient;
        this.userServiceUrl = userServiceUrl;
    }

    public UserDto getUserByEmail(String email) {

        return webClient.get()
                .uri(userServiceUrl + "/users/email/{email}", email)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
    public UserDto validateUser(String email, String password) {

        return webClient.post()
                .uri(userServiceUrl + "/users/validate")
                .bodyValue(new LoginRequest(email, password))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

}

