package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment create(Equipment equipment){ return  equipmentRepository.save(equipment);}
}
