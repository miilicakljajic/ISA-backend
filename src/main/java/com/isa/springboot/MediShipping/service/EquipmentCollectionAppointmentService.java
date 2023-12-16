package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.mapper.EquipmentCollectionAppointmentMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.mapper.UserMapper;
import com.isa.springboot.MediShipping.repository.EquipmentCollectionAppointmentRepository;
import com.isa.springboot.MediShipping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EquipmentCollectionAppointmentService {
    @Autowired
    private EquipmentCollectionAppointmentRepository equipmentCollectionAppointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private EquipmentCollectionAppointmentMapper mapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired MailService mailService;

    @Autowired AuthService authService;

    @Autowired UserMapper userMapper;
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

    private boolean alreadyExists(long companyId,LocalDateTime appointmentTime){
        Optional<Company> company = companyService.getCompanyById(companyId);

        if(company.isPresent()){
            for (EquipmentCollectionAppointment a : company.get().getAllAppointments()){
                if(a.getDate().equals(appointmentTime)){
                    System.out.println("already exists");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean equipmentOverlap(EquipmentCollectionAppointment newApp)
    {
        for(EquipmentCollectionAppointment app : equipmentCollectionAppointmentRepository.findAll())
        {
            if(app.getDate().equals(newApp.getDate()) && app.getAdminFirstname().equals(newApp.getAdminFirstname())
            && app.getAdminLastname().equals(newApp.getAdminLastname()))
                for(Equipment eq: app.getEquipment())
                    for(Equipment eq2: newApp.getEquipment())
                        if(eq.getId() == eq2.getId())
                            return true;
        }
        return false;
    }

    private EquipmentCollectionAppointment setAdmin(EquipmentCollectionAppointment newApp)
    {
        ArrayList<String> admins = new ArrayList<String>();
        for(EquipmentCollectionAppointment app : equipmentCollectionAppointmentRepository.findAll())
        {
            if(app.getDate().equals(newApp.getDate()))
                if(!admins.contains(app.getAdminFirstname()+"|"+app.getAdminLastname()))
                    admins.add(app.getAdminFirstname()+"|"+app.getAdminLastname());
        }

        for(User user: userRepository.findAll())
        {
            if(user.hasRole("ROLE_ADMIN") && !admins.contains(user.getFirstName()+"|"+user.getLastName()))
            {
                newApp.setAdminFirstname(user.getFirstName());
                newApp.setAdminLastname(user.getLastName());
                return newApp;
            }
        }
        return newApp;
    }

    public EquipmentCollectionAppointmentDto create(long companyId,EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){

        EquipmentCollectionAppointment appointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);

        boolean isValid = isDateTimeValid(companyId,equipmentCollectionAppointmentDto.date, equipmentCollectionAppointmentDto.duration);
        boolean alreadyExists = alreadyExists(companyId,equipmentCollectionAppointmentDto.date);

        if(isValid && alreadyExists) {
            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment));
        }
        else{
            System.out.println("ne valja datum");
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
            appointment.get().setReserved(updatedAppointment.isReserved());

            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment.get()));
        }
        return  null;
    }

    public EquipmentCollectionAppointmentDto finalizeAppointment(long userid, EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){

        EquipmentCollectionAppointment updatedAppointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<EquipmentCollectionAppointment> appointment = equipmentCollectionAppointmentRepository.findById(equipmentCollectionAppointmentDto.id);
        Optional<User> user = authService.getUserById(userid);
        if(appointment.isPresent() && user.isPresent()){
            try {
                updatedAppointment.setReserved(true);
                User updatedUser = user.get();
                updatedUser.addApointment(appointment.get());
                authService.updateUser(updatedUser.getId(), userMapper.convertToRegisterDto(updatedUser));
                mailService.sendAppointmentMail(user.get().getEmail(),updatedAppointment);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(updatedAppointment));
        }
        return  null;
    }

    public EquipmentCollectionAppointmentDto finalizeEmergencyAppointment(long userid, EquipmentCollectionAppointmentDto dto)
    {
        EquipmentCollectionAppointment newApp = mapper.convertToEntity(dto);
        Optional<User> user = userRepository.findById(userid);
        if(!equipmentOverlap(newApp))
            newApp = setAdmin(newApp);
        if(user.isPresent())
        {
            user.get().addApointment(newApp);
            userRepository.save(user.get());
        }
        return mapper.convertToDto(newApp);
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

            appointment.get().setReserved(true);
            appointment.get().setEquipment(equipment);

            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment.get()));
        }
        return  null;
    }
}
