package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.dto.EquipmentDto;
import com.isa.springboot.MediShipping.dto.ResponseDto;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentCollectionAppointmentMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentMapper;
import com.isa.springboot.MediShipping.mapper.UserMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.repository.EquipmentCollectionAppointmentRepository;
import com.isa.springboot.MediShipping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private CompanyRepository companyRepository;
    @Autowired
    private EquipmentCollectionAppointmentMapper mapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    MailService mailService;
    @Autowired
    AuthService authService;
    @Autowired
    UserMapper userMapper;

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

    private boolean equipmentOverlap(EquipmentCollectionAppointment newApp)
    {
        //FIX LATER
        /*
        for(EquipmentCollectionAppointment app : equipmentCollectionAppointmentRepository.findAll())
        {
            boolean upperBound = newApp.getDate().toEpochSecond(ZoneOffset.UTC) < (app.getDate().toEpochSecond(ZoneOffset.UTC) + app.getDuration()*60);
            boolean lowerBound = app.getDate().toEpochSecond(ZoneOffset.UTC) < newApp.getDate().toEpochSecond(ZoneOffset.UTC);
            if(app.getDate().equals(newApp.getDate()) || (upperBound && lowerBound))
                for(Equipment eq: app.getEquipment())
                    for(Equipment eq2: newApp.getEquipment())
                        if(eq.getId() == eq2.getId())
                            return true;
        }*/
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
        Optional<Company> company = companyService.getCompanyById(companyId);
        boolean isValid = isDateTimeValid(companyId,equipmentCollectionAppointmentDto.date, equipmentCollectionAppointmentDto.duration);
        boolean alreadyExists = alreadyExists(companyId,equipmentCollectionAppointmentDto);

        if(isValid && !alreadyExists && company.isPresent()) {
            appointment.setCompany(company.get());
            company.get().getAllAppointments().add(appointment);;
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
            appointment.get().setItems(updatedAppointment.getItems());
            appointment.get().setDate(updatedAppointment.getDate());
            appointment.get().setReserved(updatedAppointment.isReserved());

            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment.get()));
        }
        return  null;
    }

    public ResponseDto finalizeAppointment(long companyid, long userid, EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        Optional<Company> temp = companyService.getCompanyById(companyid);
        EquipmentCollectionAppointment updatedAppointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<EquipmentCollectionAppointment> appointment = equipmentCollectionAppointmentRepository.findById(equipmentCollectionAppointmentDto.id);
        Optional<User> user = authService.getUserById(userid);
        if(appointment.isPresent() && user.isPresent()){
            try {
                updatedAppointment.setCompany(temp.get());
                updatedAppointment.setReserved(true);
                User updatedUser = user.get();
                updatedUser.addApointment(updatedAppointment);
                authService.updateUser(updatedUser.getId(), userMapper.convertToRegisterDto(updatedUser));
                mailService.sendAppointmentMail(user.get().getEmail(),updatedAppointment);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return new ResponseDto(200, "OK");
        }
        return new ResponseDto(400, "Unknown error");
    }

    public ResponseDto finalizeEmergencyAppointment(long companyid, long userid, EquipmentCollectionAppointmentDto dto)
    {
        EquipmentCollectionAppointment newApp = mapper.convertToEntity(dto);
        boolean overlap = equipmentOverlap(newApp);
        boolean dateValid = isDateTimeValid(companyid,newApp.getDate(), newApp.getDuration());
        if(!overlap && dateValid) {
            Optional<User> user = userRepository.findById(userid);
            Optional<Company> company = companyService.getCompanyById(companyid);
            newApp = setAdmin(newApp);
            if (user.isPresent() && company.isPresent()) {
                //newApp = mapper.convertToEntity(create(companyid, mapper.convertToDto(newApp)));
                newApp.setCompany(company.get());
                user.get().addApointment(newApp);
                authService.updateUser(userid, userMapper.convertToRegisterDto(user.get()));
                //company.get().addAppointment(newApp);
                //companyService.updateCompany(companyid, companyMapper.convertToCompanyDto(company.get()));
                try {
                    mailService.sendAppointmentMail(user.get().getEmail(), newApp);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
            return new ResponseDto(200, "OK");
        }
        if(overlap)
            return new ResponseDto(400, "Failed: overlaps with existing appointment");
        else if(!dateValid)
            return new ResponseDto(400, "Company not available at that time");
        else
            return new ResponseDto(400,"Unknown error");
    }

    public void deleteById(long id){
        equipmentCollectionAppointmentRepository.deleteById(id);
    }
}
