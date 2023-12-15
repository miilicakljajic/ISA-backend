package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @PostMapping
    public EquipmentDto create(@RequestBody EquipmentDto equipmentDto){ return equipmentService.create(equipmentDto);}

    @PutMapping
    public EquipmentDto update(@RequestBody EquipmentDto equipmentDto) {
        return equipmentService.update(equipmentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        equipmentService.deleteById(id);
    }
}
