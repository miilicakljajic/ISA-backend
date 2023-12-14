package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentMapper equipmentMapper;

    public EquipmentDto create(EquipmentDto equipmentDto){

        Equipment newEquipment = equipmentMapper.convertToEntity(equipmentDto);

        return equipmentMapper.convertToDto(equipmentRepository.save(newEquipment));
    }

    public EquipmentDto findEquipmentById(long id){
        Optional<Equipment> equipment = equipmentRepository.findById(id);

        if(equipment.isPresent()) {
            return equipmentMapper.convertToDto(equipment.get());
        }

        return null;
    }

    public List<EquipmentDto> findByCompanyId(long id){
        List<Equipment> equipmentList = equipmentRepository.findByCompanyId(id);
        List<EquipmentDto> equipmentDtos = new ArrayList<EquipmentDto>();

        for(Equipment e : equipmentList){
            equipmentDtos.add(equipmentMapper.convertToDto(e));
        }

        return  equipmentDtos;
    }

    public EquipmentDto update(EquipmentDto updatedEquipmentDto){
        Optional<Equipment> equipment = equipmentRepository.findById(updatedEquipmentDto.id);
        Equipment updatedEquipment = equipmentMapper.convertToEntity(updatedEquipmentDto);

        if(equipment.isPresent()) {
            Equipment existingEquipment = equipment.get();
            existingEquipment.setName(updatedEquipment.getName());
            existingEquipment.setDescription(updatedEquipment.getDescription());
            existingEquipment.setType(updatedEquipment.getType());

            return  equipmentMapper.convertToDto(equipmentRepository.save(existingEquipment));
        }
        return null;
    }

    public void deleteById(long id){
        equipmentRepository.deleteById(id);
    }
}
