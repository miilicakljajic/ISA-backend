package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout",value = "0")})
    public Company  save(Company company);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout",value = "0")})
    public Optional<Company> findById(@Param("id")Long id);
}
