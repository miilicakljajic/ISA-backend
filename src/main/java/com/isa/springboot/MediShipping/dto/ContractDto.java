package com.isa.springboot.MediShipping.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ContractDto {
    public long companyId;
    public List<String> items;
    public String deliveryDate;
    public Boolean canDeliver;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
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

    public ContractDto() {
    }

    public ContractDto(long companyId, List<String> items, String deliveryDate, boolean canDeliver) {
        this.companyId = companyId;
        this.items = items;
        this.deliveryDate = deliveryDate;
        this.canDeliver = canDeliver;
    }
}
