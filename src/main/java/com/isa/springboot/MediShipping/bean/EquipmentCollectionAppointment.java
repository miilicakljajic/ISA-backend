package com.isa.springboot.MediShipping.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isa.springboot.MediShipping.util.AppointmentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "appointments")
public class EquipmentCollectionAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(cascade = {CascadeType.ALL,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinTable(name = "appointment_items",
        joinColumns = @JoinColumn(name = "appointment_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "orderitem_id", referencedColumnName = "id"))

    private Set<OrderItem> items;
    private String adminFirstname;
    private String adminLastname;
    private LocalDateTime date;
    private int duration;
    private AppointmentStatus status;
    private byte[] qr;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    private  User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getQr() {
        return qr;
    }

    public void setQr(byte[] qr) {
        this.qr = qr;
    }

    @Override
    public String toString() {
        return "EquipmentCollectionAppointment{" +
                "id=" + id +
                ", equipment=" + items +
                ", adminFirstname='" + adminFirstname + '\'' +
                ", adminLastname='" + adminLastname + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", status=" + status +
                ", company=" + company +
                '}';
    }
}