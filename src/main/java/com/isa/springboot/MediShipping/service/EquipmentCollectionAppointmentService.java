package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.mapper.EquipmentCollectionAppointmentMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.repository.EquipmentCollectionAppointmentRepository;
import com.isa.springboot.MediShipping.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EquipmentCollectionAppointmentService {
    @Autowired
    private EquipmentCollectionAppointmentRepository equipmentCollectionAppointmentRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EquipmentCollectionAppointmentMapper mapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    private String[] getCompanyWorkingHours(long id){
        Optional<Company> company = companyService.getCompanyById(id);
        if(company.isPresent()){
            String[] workingHours = company.get().getWorkingHours().split("-");

            return workingHours;
        }
        return null;
    }

    private boolean isDateTimeValid(long companyId, LocalDateTime appointmentTime,int duration){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime goodFormat = LocalDateTime.parse(appointmentTime.format(formatter), formatter);
        int durationInHours = (int)Math.ceil(duration/60.0);
        String[] workingHours = getCompanyWorkingHours(companyId);

        if(goodFormat.getDayOfWeek() != DayOfWeek.SATURDAY && goodFormat.getDayOfWeek() != DayOfWeek.SUNDAY){
            if(goodFormat.getHour() > Integer.parseInt(workingHours[0]) && goodFormat.getHour() + durationInHours < Integer.parseInt(workingHours[1])) {
                return true;
            }
        }
        return  false;
    }

    private boolean alreadyExists(long companyId,EquipmentCollectionAppointmentDto dto){
        Optional<Company> company = companyService.getCompanyById(companyId);

        if(company.isPresent()){
            for (EquipmentCollectionAppointment a : company.get().getAllAppointments()){

                if(a.getDate().equals(dto.date) && a.getAdminLastname().equals(dto.getAdminLastname())){
                    System.out.println("Appointment in this timeslot already exists");
                    return true;
                }
            }
        }

        return false;
    }
    public EquipmentCollectionAppointmentDto create(long companyId,EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){

        EquipmentCollectionAppointment appointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<Company> company = companyService.getCompanyById(companyId);
        boolean isValid = isDateTimeValid(companyId,equipmentCollectionAppointmentDto.date, equipmentCollectionAppointmentDto.duration);
        boolean alreadyExists = alreadyExists(companyId,equipmentCollectionAppointmentDto);

        if(isValid && !alreadyExists) {
            company.get().getAllAppointments().add(appointment);
            companyRepository.save(company.get());
            //return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment));
            return mapper.convertToDto(appointment);
        }
        else{
            return null;
        }
    }
    public EquipmentCollectionAppointmentDto update(EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        EquipmentCollectionAppointment updatedAppointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<EquipmentCollectionAppointment> appointment = equipmentCollectionAppointmentRepository.findById(equipmentCollectionAppointmentDto.id);

        if(appointment.isPresent()){
            appointment.get().setAdminFirstname(updatedAppointment.getAdminFirstname());
            appointment.get().setAdminLastname(updatedAppointment.getAdminLastname());
            appointment.get().setEquipment(updatedAppointment.getEquipment());
            appointment.get().setDate(updatedAppointment.getDate());
            appointment.get().setStatus(updatedAppointment.getStatus());

            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment.get()));
        }
        return  null;
    }

    public void deleteById(long id){
        equipmentCollectionAppointmentRepository.deleteById(id);
    }


    //nisam testirao ne znam da li radi
    public EquipmentCollectionAppointmentDto scheduleAppointment(long id, List<EquipmentDto> equipmentDtos){
        Optional<EquipmentCollectionAppointment> appointment = equipmentCollectionAppointmentRepository.findById(id);
        Set<Equipment> equipment = new HashSet<>();

        if(appointment.isPresent()){

            for (EquipmentDto e : equipmentDtos){
                equipment.add(equipmentMapper.convertToEntity(e));
            }

            appointment.get().setStatus(AppointmentStatus.RESERVED);
            appointment.get().setEquipment(equipment);

            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment.get()));
        }
        return  null;
    }
}
