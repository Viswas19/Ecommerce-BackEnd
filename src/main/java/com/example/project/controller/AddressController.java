package com.example.project.controller;


import com.example.project.dto.AddressDto;
import com.example.project.dto.Response;
import com.example.project.service.implementation.AddressServiceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressServiceimpl addressServiceimpl;

    @PostMapping
    public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto){
        return ResponseEntity.ok(addressServiceimpl.saveandUpdateAddress(addressDto));
    }

}
