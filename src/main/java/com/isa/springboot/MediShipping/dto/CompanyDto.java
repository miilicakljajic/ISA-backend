package com.isa.springboot.MediShipping.dto;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;

import java.util.Set;

public class CompanyDto {
    public String name;
    public Address address;
    public Set<Equipment> equipment;
    public Set<RegisterDto> companyManagers;
    public Set<EquipmentCollectionAppointment> availableAppointments;
    public Double averageRating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Set<RegisterDto> getCompanyManagers() {
        return companyManagers;
    }

    public void setCompanyManagers(Set<RegisterDto> companyManagers) {
        this.companyManagers = companyManagers;
    }

    public Set<EquipmentCollectionAppointment> getAvailableAppointments() {
        return availableAppointments;
    }

    public void setAvailableAppointments(Set<EquipmentCollectionAppointment> availableAppointments) {
        this.availableAppointments = availableAppointments;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
