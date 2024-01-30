package com.isa.springboot.MediShipping.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

public class Contract {
    private long id;
    private long companyId;
    private List<String> items;
    private String deliveryDate;

    private Boolean canDeliver;
}
