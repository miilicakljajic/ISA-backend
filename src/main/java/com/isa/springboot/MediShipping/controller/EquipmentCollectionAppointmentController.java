package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.dto.ResponseDto;
import com.isa.springboot.MediShipping.service.EquipmentCollectionAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/{companyId}")
    public EquipmentCollectionAppointmentDto update(@PathVariable long companyId,@RequestBody EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        return  service.update1(equipmentCollectionAppointmentDto,companyId);
    }
    @PutMapping("/finalize/{companyid}/{id}")
    public ResponseDto finalize(@PathVariable long companyid,  @PathVariable long id, @RequestBody EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        return  service.finalizeAppointment(companyid, id, equipmentCollectionAppointmentDto);
    }

    @PostMapping("/emergency/{companyid}/{id}")
    public ResponseDto finalizeEmergency(@PathVariable long companyid, @PathVariable long id, @RequestBody EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        return service.finalizeEmergencyAppointment(companyid, id, equipmentCollectionAppointmentDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }

    /*@GetMapping("/byCompany/{id}")
    public List<UserAppointmentDto> getUsersWithUpcomingAppointments(@PathVariable long id){
        return service.getUsersWithUpcomingAppointments(id);
    }*/

    //TODO FRONT
    @GetMapping("/byUser/{id}")
    public List<EquipmentCollectionAppointment> getAppointments(@PathVariable Long id) {
        return service.getAppointments(id);
    }

    //TODO FRONT
    @PostMapping("/byUser/cancel/{id}")
    public void cancelAppointment(@PathVariable Long id, @RequestBody EquipmentCollectionAppointmentDto appointment)
    {
        service.cancelAppointment(id, appointment);
    }
}