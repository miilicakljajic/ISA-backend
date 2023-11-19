package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address create(Address address){ return  addressRepository.save(address); }
}
