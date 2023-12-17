package com.isa.springboot.MediShipping.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "appointments")
public class EquipmentCollectionAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinTable(name = "equipment_appointment",
        joinColumns = @JoinColumn(name = "appointment_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private Set<Equipment> equipment;
    private String adminFirstname;
    private String adminLastname;
    private LocalDateTime date;
    private int duration;
    private boolean reserved;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "EquipmentCollectionAppointment{" +
                "id=" + id +
                ", equipment=" + equipment +
                ", adminFirstname='" + adminFirstname + '\'' +
                ", adminLastname='" + adminLastname + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", reserved=" + reserved +
                '}';
    }
}
