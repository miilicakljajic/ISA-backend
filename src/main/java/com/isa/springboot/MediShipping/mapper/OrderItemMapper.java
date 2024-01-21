package com.isa.springboot.MediShipping.mapper;

import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.OrderItem;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.dto.OrderItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public  OrderItemMapper(ModelMapper modelMapper){ this.modelMapper = modelMapper;}

    public OrderItemDto convertToDto(OrderItem item) { return  modelMapper.map(item, OrderItemDto.class);}
    public OrderItem convertToEntity(OrderItemDto itemDto) {return  modelMapper.map(itemDto, OrderItem.class);}
}
