package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
