package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.service.EquipmentCollectionAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/appointments")
public class EquipmentCollectionAppointmentController {
    @Autowired
    private EquipmentCollectionAppointmentService service;
    @PostMapping("/{companyId}")
    public EquipmentCollectionAppointmentDto create(@PathVariable Long companyId, @RequestBody EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        return service.create(companyId,equipmentCollectionAppointmentDto);
    }
    @PutMapping
    public EquipmentCollectionAppointmentDto update(@RequestBody EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        return  service.update(equipmentCollectionAppointmentDto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }
}