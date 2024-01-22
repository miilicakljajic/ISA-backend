package com.isa.springboot.MediShipping.dto;

import com.isa.springboot.MediShipping.util.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class EquipmentCollectionAppointmentDto {
    public long id;
    public String adminFirstname;
    public String adminLastname;
    public LocalDateTime date;

    public Set<EquipmentDto> equipment;
    public int duration;

    public AppointmentStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdminFirstname() {
        return adminFirstname;
    }

    public void setAdminFirstname(String adminFirstname) {
        this.adminFirstname = adminFirstname;
    }

    public String getAdminLastname() {
        return adminLastname;
    }

    public void setAdminLastname(String adminLastname) {
        this.adminLastname = adminLastname;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Set<EquipmentDto> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<EquipmentDto> equipment) {
        this.equipment = equipment;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
