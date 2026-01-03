package com.auth_service.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String email;
    private boolean isActive;
    private String role;
}
