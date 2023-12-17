package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {

}
