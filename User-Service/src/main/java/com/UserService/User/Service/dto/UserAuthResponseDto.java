package com.UserService.User.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAuthResponseDto {

    private Long id;
    private String email;
    private String role;
    private boolean active;
}