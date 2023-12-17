package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.dto.AddressDto;
import com.isa.springboot.MediShipping.mapper.AddressMapper;
import com.isa.springboot.MediShipping.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper mapper;

    public AddressDto create(AddressDto addressDto){
        Address newAddress = mapper.convertToEntity(addressDto);
        return  mapper.convertToAddressDto(addressRepository.save(newAddress));
    }
}
