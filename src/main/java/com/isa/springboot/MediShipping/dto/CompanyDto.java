package com.isa.springboot.MediShipping.dto;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;

import java.util.Set;

public class CompanyDto {
    public long id;

    public String name;
    public AddressDto addressDto;

    public Set<EquipmentDto> equipment;
    public Set<RegisterDto> companyManagers;
    public Set<EquipmentCollectionAppointmentDto> availableAppointments;
    public Double averageRating;
    public String workingHours;
}
