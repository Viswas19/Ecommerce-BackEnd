package com.example.project.service.interf;


import com.example.project.dto.LoginRequest;
import com.example.project.dto.Response;
import com.example.project.dto.UserDto;
import com.example.project.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();

}
