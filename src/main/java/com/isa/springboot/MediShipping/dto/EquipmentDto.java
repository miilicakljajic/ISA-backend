package com.isa.springboot.MediShipping.dto;

public class EquipmentDto {
    public long id;

    public String name;
    public String description;
    public String type;

    public EquipmentDto(long id, String name, String description, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
