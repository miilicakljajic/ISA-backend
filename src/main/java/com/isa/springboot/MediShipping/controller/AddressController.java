package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.dto.AddressDto;
import com.isa.springboot.MediShipping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping
    public AddressDto create(@RequestBody AddressDto addressDto){ return  addressService.create(addressDto);}
}