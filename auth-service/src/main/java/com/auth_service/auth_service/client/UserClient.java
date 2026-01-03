package com.auth_service.auth_service.client;

import com.auth_service.auth_service.dto.EmailRequestDto;
import com.auth_service.auth_service.dto.LoginRequest;
import com.auth_service.auth_service.dto.SignUpRequest;
import com.auth_service.auth_service.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service

public class UserClient {

    private final WebClient webClient;
    private final String userServiceUrl;
    private final String emailServiceUrl;

    public UserClient(WebClient webClient,
                      @Value("${user.service.url}") String userServiceUrl,@Value("${email.service.url}") String emailServiceUrl) {
        this.webClient = webClient;
        this.userServiceUrl = userServiceUrl;
        this.emailServiceUrl = emailServiceUrl;
    }


    public UserDto validateUser(String email, String password) {

        return webClient.post()
                .uri(userServiceUrl + "/users/validate")
                .bodyValue(new LoginRequest(email, password))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public void createUser(SignUpRequest dto) {
        webClient.post()
                .uri(userServiceUrl + "/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
    public void sendEmail(String to, String subject, String body, String activationLink) {
        webClient.post()
                .uri(emailServiceUrl + "/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new EmailRequestDto(to, subject, body, activationLink))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


}

