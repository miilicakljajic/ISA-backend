package com.isa.springboot.MediShipping.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;
    @OneToOne
    private Address address;
    @Column(name="description",nullable = false)
    private String description;

    @Column(name="averageRating",nullable = false)
    private Double averageRating;
    @OneToMany(mappedBy = "companies")
    private Set<EquipmentCollectionAppointment> availableAppointments;
    @OneToMany(mappedBy = "companies")
    private Set<Equipment> equipment;
    @OneToMany(mappedBy = "companies")
    private Set<User> companyManagers;

    public Long getId() {
        return id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Set<EquipmentCollectionAppointment> getAvailableAppointments() {
        return availableAppointments;
    }

    public void setAvailableAppointments(Set<EquipmentCollectionAppointment> availableAppointments) {
        this.availableAppointments = availableAppointments;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Set<User> getCompanyManagers() {
        return companyManagers;
    }

    public void setCompanyManagers(Set<User> companyManagers) {
        this.companyManagers = companyManagers;
    }
}
