package com.UserService.User.Service.service;

import com.UserService.User.Service.dto.UserAuthRequestDto;
import com.UserService.User.Service.dto.UserAuthResponseDto;
import com.UserService.User.Service.dto.UserRequestDto;
import com.UserService.User.Service.dto.UserResponceDto;


public interface UserService {

    UserResponceDto createUser(UserRequestDto userRequest);

    UserResponceDto getUserById(Long userId);

    UserResponceDto getUserByEmail(String email);

    UserResponceDto updateUser(Long userId, UserRequestDto userRequest);

    UserResponceDto activateUser(String email);


    UserResponceDto deactivateUser(String email);

    UserAuthResponseDto validateUser(UserAuthRequestDto userAuthRequestDto);

    void deleteUser(Long userId);

}
