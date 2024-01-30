package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.mapper.OrderItemMapper;
import com.isa.springboot.MediShipping.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository itemRepository;
    @Autowired
    private OrderItemMapper itemMapper;

    //Todo if needed
}
