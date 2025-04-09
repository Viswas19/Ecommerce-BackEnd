package com.example.project.service.interf;

import com.example.project.dto.AddressDto;
import com.example.project.dto.Response;

public interface AddressService {
    Response saveandUpdateAddress(AddressDto addressDto);
}
