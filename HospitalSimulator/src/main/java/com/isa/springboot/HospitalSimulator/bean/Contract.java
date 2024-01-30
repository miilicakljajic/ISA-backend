package com.isa.springboot.HospitalSimulator.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class Contract {
    private long companyId;
    private ArrayList<String> items;
    private String deliveryDate;
    private Boolean canDeliver;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getCanDeliver() {
        return canDeliver;
    }

    public void setCanDeliver(Boolean canDeliver) {
        this.canDeliver = canDeliver;
    }

    public Contract(){

    }
    public Contract(long companyId, ArrayList<String> items, String deliveryDate) {
        this.companyId = companyId;
        this.items = items;
        this.deliveryDate = deliveryDate;
        this.canDeliver = false;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "companyId=" + companyId +
                ", items=" + items +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
