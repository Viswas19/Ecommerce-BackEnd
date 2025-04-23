package com.example.project.service.implementation;


import com.example.project.exception.NotFoundException;
//import com.amazonaws.services.kms.model.NotFoundException;
import com.example.project.Mapper.EntityDtoMapper;
import com.example.project.dto.LoginRequest;
import com.example.project.dto.Response;
import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import com.example.project.enums.UserRole;
import com.example.project.exception.InvalidCredentialsException;
import com.example.project.repository.UserRepo;
import com.example.project.security.JwtUtils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service

@RequiredArgsConstructor


public class UserServiceimpl {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EntityDtoMapper entityDtoMapper;
    private final JwtUtils jwtUtils;
    private final Validator validator;



    public Response registerUser(UserDto registrationRequest) {
        UserRole role = UserRole.USER;

        String email = registrationRequest.getEmail().trim();
        String password = registrationRequest.getPassword();

        // ✅ Check for spaces in email and password
        if (email.matches(".*\\s+.*")) {
            throw new InvalidCredentialsException("Email must not contain spaces");
        }

        if (password.matches(".*\\s+.*")) {
            throw new InvalidCredentialsException("Password must not contain spaces");
        }


        if(registrationRequest.getRole()!=null && registrationRequest.getRole().equalsIgnoreCase("admin")){
            role = UserRole.ADMIN;
        }

        User user = User.builder().name(registrationRequest.getName()).email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword())).phoneNumber(registrationRequest.getPhoneNumber())
                .role(role).build();


        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            String message = violations.iterator().next().getMessage(); // get first error message
            throw new InvalidCredentialsException(message); // Or create a new exception like InvalidInputException
        }

        User savedUser = userRepo.save(user);

        UserDto userDto = entityDtoMapper.mapUserToDto(savedUser);
        return Response.builder().message("User Successfully Added").user(userDto).build();
    }


    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFoundException("Email Not Found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email Or Password");
        }
        String token = jwtUtils.generateToken(user);
        UserDto userDto = entityDtoMapper.mapUserToDto(user);

        return Response.builder().message("User Successfully LoggedIn").token(token).role(user.getRole().name()).build();
    }


    public Response getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(entityDtoMapper::mapUserToDto).toList();
        return Response.builder().userList(userDtos).build();
    }


    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  email = authentication.getName();
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not found"));
    }


    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);

        return Response.builder()
                .user(userDto)
                .build();
    }
}
