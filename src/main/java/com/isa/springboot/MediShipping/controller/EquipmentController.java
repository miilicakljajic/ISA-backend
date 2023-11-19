package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.Equipment;
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
    public Equipment create(@RequestBody Equipment equipment){ return equipmentService.create(equipment);}
}
