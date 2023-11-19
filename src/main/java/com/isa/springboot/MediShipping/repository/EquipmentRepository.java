package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
}
