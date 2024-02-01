package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentCollectionAppointmentRepository extends JpaRepository<EquipmentCollectionAppointment,Long> {
    public EquipmentCollectionAppointment save(EquipmentCollectionAppointment appointment);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout",value = "0")})
    public Optional<EquipmentCollectionAppointment> findById(@Param("id")Long id);


}
