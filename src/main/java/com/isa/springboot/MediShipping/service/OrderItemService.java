package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.mapper.OrderItemMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.repository.EquipmentRepository;
import com.isa.springboot.MediShipping.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository itemRepository;
    @Autowired
    private OrderItemMapper itemMapper;

    //Todo if needed
}
