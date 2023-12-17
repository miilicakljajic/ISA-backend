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

    public AddressDto address;

    public Set<EquipmentDto> equipment;
    public Set<RegisterDto> companyManagers;
    public Set<EquipmentCollectionAppointmentDto> allAppointments;
    public Double averageRating;
    public String workingHours;
    public String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public Set<EquipmentDto> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<EquipmentDto> equipment) {
        this.equipment = equipment;
    }

    public Set<RegisterDto> getCompanyManagers() {
        return companyManagers;
    }

    public void setCompanyManagers(Set<RegisterDto> companyManagers) {
        this.companyManagers = companyManagers;
    }

    public Set<EquipmentCollectionAppointmentDto> getAllAppointments() {
        return allAppointments;
    }

    public void setAllAppointments(Set<EquipmentCollectionAppointmentDto> allAppointments) {
        this.allAppointments = allAppointments;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}