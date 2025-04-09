package com.example.project.service.implementation;


import com.example.project.dto.AddressDto;
import com.example.project.dto.Response;
import com.example.project.entity.Address;
import com.example.project.entity.User;
import com.example.project.repository.AddressRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceimpl {



    private final AddressRepo addressRepo;
    private final UserServiceimpl userServiceimpl;



    public Response saveandUpdateAddress(AddressDto addressDto) {
        User user = userServiceimpl.getLoginUser();
        Address address = user.getAddress();

        if (address == null){
            address = new Address();
            address.setUser(user);
        }
        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        addressRepo.save(address);

        String message = (user.getAddress() == null) ? "Address successfully created" : "Address successfully updated";
        return Response.builder().message(message).build();
    }
}
