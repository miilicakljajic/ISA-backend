package com.isa.springboot.MediShipping.mapper;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private final ModelMapper modelMapper;

    public AddressMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Address convertToEntity(AddressDto dto) { return modelMapper.map(dto, Address.class);}

    public AddressDto convertToAddressDto(Address address) { return modelMapper.map(address, AddressDto.class); }
}
