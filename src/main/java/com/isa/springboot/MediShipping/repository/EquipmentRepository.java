package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {

    @Query(nativeQuery = true,value = "SELECT e.id,e.description,e.name,e.type FROM public.equipment e\n" +
            "JOIN public.companies_equipment ce ON e.id = ce.equipment_id\n" +
            "WHERE ce.company_id = ?1;\n")
    public List<Equipment> findByCompanyId(long id);
}
