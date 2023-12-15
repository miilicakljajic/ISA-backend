package com.isa.springboot.MediShipping.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class EquipmentCollectionAppointmentDto {
    public long id;
    public String adminFirstname;
    public String adminLastname;
    public LocalDateTime date;

    public Set<EquipmentDto> equipment;
    public int duration;
    public boolean isReserved;
}
