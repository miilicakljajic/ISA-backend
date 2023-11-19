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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Address address;
    @Column(name="description",nullable = false)
    private String description;

    @Column(name="averageRating",nullable = false)
    private Double averageRating;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Set<EquipmentCollectionAppointment> allAppointments;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Set<Equipment> equipment;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "companyInfo",fetch = FetchType.LAZY)
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

    public Set<EquipmentCollectionAppointment> getAllAppointments() {
        return allAppointments;
    }

    public void setAllAppointments(Set<EquipmentCollectionAppointment> allAppointments) {
        this.allAppointments = allAppointments;
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
