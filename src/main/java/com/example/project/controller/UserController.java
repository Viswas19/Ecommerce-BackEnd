package com.example.project.controller;


import com.example.project.dto.Response;
import com.example.project.service.implementation.UserServiceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceimpl userServiceimpl;
    @GetMapping("")
   @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userServiceimpl.getAllUsers());
    }

    @GetMapping("/my-info")
    public ResponseEntity<Response> getUserInfoAndOrderHistory(){
        return ResponseEntity.ok(userServiceimpl.getUserInfoAndOrderHistory());
    }

}
