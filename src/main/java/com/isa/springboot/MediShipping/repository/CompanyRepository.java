package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
