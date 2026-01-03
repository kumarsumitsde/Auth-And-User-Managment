package com.UserService.User.Service.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponceDto {

    private Long id;
    private String name;
    private String email;
    private Long phone;
    private String role;
    private boolean isActive;

}
