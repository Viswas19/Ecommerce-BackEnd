package com.example.project.controller;


import com.example.project.dto.LoginRequest;
import com.example.project.dto.Response;
import com.example.project.dto.UserDto;

import com.example.project.service.implementation.UserServiceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceimpl userServiceimpl;
    @PostMapping("/register")
    public ResponseEntity<Response>registerUser(@RequestBody UserDto registrationRequest){
        return ResponseEntity.ok(userServiceimpl.registerUser(registrationRequest));
    }

    @PutMapping("/login")
    public ResponseEntity<Response>loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userServiceimpl.loginUser(loginRequest));
    }


}
