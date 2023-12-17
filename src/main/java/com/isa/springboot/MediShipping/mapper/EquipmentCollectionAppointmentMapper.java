package com.isa.springboot.MediShipping.mapper;

import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipmentCollectionAppointmentMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public EquipmentCollectionAppointmentMapper(ModelMapper modelMapper){ this.modelMapper = modelMapper;}

    public EquipmentCollectionAppointmentDto convertToDto(EquipmentCollectionAppointment equipmentCollectionAppointment){
        return modelMapper.map(equipmentCollectionAppointment,EquipmentCollectionAppointmentDto.class);
    }
    public EquipmentCollectionAppointment convertToEntity(EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        return  modelMapper.map(equipmentCollectionAppointmentDto,EquipmentCollectionAppointment.class);
    }
}
