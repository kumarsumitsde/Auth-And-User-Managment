package com.auth_service.auth_service.service;

import com.auth_service.auth_service.client.UserClient;
import com.auth_service.auth_service.dto.*;
import com.auth_service.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final JwtUtil jwtUtil;

    public AuthResponce login(LoginRequest request){
//        UserDto user =userClient.getUserByEmail(request.getEmail());
        UserDto user =userClient.validateUser(request.getEmail(), request.getPassword());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponce(token, true);

    }

    public String signUp(SignUpRequest dto){
        userClient.createUser(dto);
        userClient.sendEmail(dto.getEmail(),
                "Account Activation",
                "Please activate your account using the following link: ",
                "http://example.com/activate?token=ewrtgtrfthg3re");
        return "User created successfully. Activation email sent.";
    }

}
