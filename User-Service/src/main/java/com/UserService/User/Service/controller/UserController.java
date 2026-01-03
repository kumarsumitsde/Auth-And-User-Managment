package com.UserService.User.Service.controller;

import com.UserService.User.Service.dto.UserAuthRequestDto;
import com.UserService.User.Service.dto.UserAuthResponseDto;
import com.UserService.User.Service.dto.UserRequestDto;
import com.UserService.User.Service.dto.UserResponceDto;
import com.UserService.User.Service.entity.User;
import com.UserService.User.Service.repository.UserRepository;
import com.UserService.User.Service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping
    public UserResponceDto createUser(@Valid @RequestBody UserRequestDto request){
        return userService.createUser(request);
    }

    @GetMapping("email/{email}")
    public UserResponceDto getUserByEmailid(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping("id/{id}")
    public UserResponceDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("id/{id}")
    public UserResponceDto updateUser(@PathVariable Long id , @Valid @RequestBody UserRequestDto request){
        return userService.updateUser(id,request);
    }
    @GetMapping("user/validate")
    public UserResponceDto validate(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
    @PostMapping("/validate")
    public UserAuthResponseDto validateUser(@RequestBody UserAuthRequestDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive()) {
            throw new RuntimeException("User is inactive");
        }

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new UserAuthResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive()
        );
    }


}
