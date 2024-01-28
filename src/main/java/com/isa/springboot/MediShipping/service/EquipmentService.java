package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.repository.EquipmentRepository;
import com.isa.springboot.MediShipping.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        if(equipment.isPresent()) {
            Equipment existingEquipment = equipment.get();
            existingEquipment.setName(updatedEquipment.getName());
            existingEquipment.setDescription(updatedEquipment.getDescription());
            existingEquipment.setType(updatedEquipment.getType());
            existingEquipment.setCount(updatedEquipment.getCount());
            existingEquipment.setPrice(updatedEquipment.getPrice());

            return  equipmentMapper.convertToDto(equipmentRepository.save(existingEquipment));
        }
        return null;
    }


    /*public boolean isEquipmentReserved(Company company,long equipmentId){

        LocalDateTime today = LocalDateTime.now();
        Set<Equipment> x = new HashSet<Equipment>(company.getEquipment());

        if(company.getAllAppointments().isEmpty()){
            return false;
        }

        for(EquipmentCollectionAppointment a : company.getAllAppointments()){
            if(a.getDate().isBefore(today))
                continue;

            for (Equipment e : x){
                if(e.getId() == equipmentId){
                    return true;
                }
            }
        }

        return false;
    }*/

    public void deleteById(long companyId,long id){
        Optional<Company> company = companyService.getCompanyById(companyId);

        Equipment equipmentForDeletion = equipmentMapper.convertToEntity(findEquipmentById(id));
        System.out.println(equipmentForDeletion);

      //  if(!isEquipmentReserved(company.get(),equipmentForDeletion.getId())){
            company.get().getEquipment().remove(equipmentForDeletion);
            companyRepository.save(company.get());
       // }
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

    public List<EquipmentDto> search(long companyId,String name,String type){
        Optional<Company> company = companyService.getCompanyById(companyId);
        ArrayList<EquipmentDto> searchedItems = new ArrayList<>();

        if(company.isPresent()) {
            for (Equipment eq : company.get().getEquipment()) {
                if (eq.getName().toLowerCase().contains(name.toLowerCase()) && eq.getType().toLowerCase().contains(type.toLowerCase()))
                    searchedItems.add(equipmentMapper.convertToDto(eq));
            }
        }
        return searchedItems;
    }
}