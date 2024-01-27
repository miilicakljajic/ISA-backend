package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/mails")
public class MailController {
    @Autowired
    MailService service;
    @PostMapping("/{userId}")
    public void sendCollectionMail(@PathVariable String userId,@RequestBody EquipmentCollectionAppointmentDto appointmentDto){
        try {
            service.sendCollectionMail(Long.parseLong(userId),appointmentDto);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
