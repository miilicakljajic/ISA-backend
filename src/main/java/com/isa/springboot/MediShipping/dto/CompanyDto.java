package com.isa.springboot.MediShipping.dto;

import com.isa.springboot.MediShipping.bean.Address;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;

import java.util.Set;

public class CompanyDto {
    public String name;
    public Address address;
    public Set<Equipment> equipment;
    public Set<EquipmentCollectionAppointment> availableAppointments;
    public Double averageRating;
}
