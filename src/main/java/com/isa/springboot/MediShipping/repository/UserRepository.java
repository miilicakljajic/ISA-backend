package com.isa.springboot.MediShipping.repository;
import com.isa.springboot.MediShipping.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
