package com.isa.springboot.MediShipping.mapper;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.dto.CompanyDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public CompanyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CompanyDto convertToCompanyDto(Company company) { return modelMapper.map(company, CompanyDto.class);}

    public Company convertToEntity(CompanyDto dto) { return modelMapper.map(dto, Company.class); }
}
