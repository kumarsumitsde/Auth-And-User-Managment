package com.auth_service.auth_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponce {

    private String Status;;
    private String message;
    private String activationLink;
}
