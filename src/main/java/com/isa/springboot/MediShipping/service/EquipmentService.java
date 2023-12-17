package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;

    public EquipmentDto create(EquipmentDto equipmentDto){

        Equipment newEquipment = equipmentMapper.convertToEntity(equipmentDto);
        return equipmentMapper.convertToDto(equipmentRepository.save(newEquipment));
    }

    public EquipmentDto findEquipmentById(long id){
        Optional<Equipment> equipment = equipmentRepository.findById(id);

        if(equipment.isPresent()) {
            return equipmentMapper.convertToDto(equipment.get());
        }

        return null;
    }

    public EquipmentDto update(EquipmentDto updatedEquipmentDto){
        Optional<Equipment> equipment = equipmentRepository.findById(updatedEquipmentDto.id);
        Equipment updatedEquipment = equipmentMapper.convertToEntity(updatedEquipmentDto);

        System.out.println(updatedEquipment.getId());
        System.out.println(updatedEquipmentDto.getId());

        if(equipment.isPresent()) {
            Equipment existingEquipment = equipment.get();
            existingEquipment.setName(updatedEquipment.getName());
            existingEquipment.setDescription(updatedEquipment.getDescription());
            existingEquipment.setType(updatedEquipment.getType());

            return  equipmentMapper.convertToDto(equipmentRepository.save(existingEquipment));
        }
        return null;
    }
    
    public void deleteById(long companyId,long id){
        Optional<Company> company = companyService.getCompanyById(companyId);
        LocalDateTime today = LocalDateTime.now();
        Equipment equipmentForDeletion = equipmentMapper.convertToEntity(findEquipmentById(id));
        equipmentForDeletion.setId(id);

        Set<Equipment> x = new HashSet<Equipment>(company.get().getEquipment());

       for(EquipmentCollectionAppointment a : company.get().getAllAppointments()){

            if(a.getDate().isAfter(today))
                continue;

            for (Equipment e : x){
            if(e.getId() == id){
                company.get().getEquipment().remove(equipmentForDeletion);
                break;
                }
            }
        }
        companyRepository.save(company.get());
       equipmentRepository.deleteById(id);
    }

    public List<EquipmentDto> searchByCompanyEqName(long companyId, String name)
    {
        Optional<Company> company = companyService.getCompanyById(companyId);
        ArrayList<EquipmentDto> searchedItems = new ArrayList<>();
        if(company.isPresent())
            for(Equipment eq : company.get().getEquipment())
                if(eq.getName().toLowerCase().contains(name.toLowerCase()))
                    searchedItems.add(equipmentMapper.convertToDto(eq));

        return searchedItems;
    }
}
