package com.isa.springboot.MediShipping.repository;

import com.isa.springboot.MediShipping.bean.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
