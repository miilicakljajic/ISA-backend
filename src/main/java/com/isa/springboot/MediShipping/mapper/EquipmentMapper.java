package com.isa.springboot.MediShipping.mapper;

import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public  EquipmentMapper(ModelMapper modelMapper){ this.modelMapper = modelMapper;}

    public EquipmentDto convertToDto(Equipment equipment) { return  modelMapper.map(equipment,EquipmentDto.class);}
    public Equipment convertToEntity(EquipmentDto equipmentDto) {return  modelMapper.map(equipmentDto,Equipment.class);}
}
