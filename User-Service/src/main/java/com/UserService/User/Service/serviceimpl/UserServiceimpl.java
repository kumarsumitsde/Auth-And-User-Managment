package com.UserService.User.Service.serviceimpl;

import com.UserService.User.Service.dto.UserAuthRequestDto;
import com.UserService.User.Service.dto.UserAuthResponseDto;
import com.UserService.User.Service.dto.UserRequestDto;
import com.UserService.User.Service.dto.UserResponceDto;
import com.UserService.User.Service.entity.Role;
import com.UserService.User.Service.entity.User;
import com.UserService.User.Service.repository.UserRepository;
import com.UserService.User.Service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserResponceDto createUser(UserRequestDto userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .phone(userRequest.getPhone())
                .role(String.valueOf(Role.Normal))
                .isActive(true)
                .build();
        User saveduser= userRepository.save(user);
        return mapToResponse(saveduser);
    }



    @Override
    public UserResponceDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Id not found"));
        return mapToResponse(user);
    }

    @Override
    public UserResponceDto getUserByEmail(String email) {
        User user =userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Email Id  not found"));
        return mapToResponse(user);
    }

    @Override
    public UserResponceDto updateUser(Long userId, UserRequestDto userRequest) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Id not found"));
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPhone(userRequest.getPhone());
        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    @Override
    public UserResponceDto activateUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Email Id not found"));
        if (user.getIsActive()){
            throw new RuntimeException("User is already active");
        }
        user.setIsActive(true);
        User activatedUser = userRepository.save(user);
        return mapToResponse(activatedUser);
    }
    @Override
    public UserResponceDto deactivateUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Email Id not found"));
        if (!user.getIsActive()){
            throw new RuntimeException("User is already deactivated");
        }
        user.setIsActive(false);
        User deactivatedUser = userRepository.save(user);
        return mapToResponse(deactivatedUser);
    }

    @Override
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Id not found"));
        userRepository.delete(user);
    }

    @Override
    public UserAuthResponseDto validateUser(UserAuthRequestDto userAuthRequestDto) {
        User user = userRepository.findByEmail(userAuthRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("Email Id not found"));

        if(!user.getIsActive()){
            throw new RuntimeException("User is not active");
        }
        if (!user.getPassword().equals(userAuthRequestDto.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new UserAuthResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive()
        );
    }

    private UserResponceDto mapToResponse(User user) {
        return UserResponceDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .build();
    }

}

