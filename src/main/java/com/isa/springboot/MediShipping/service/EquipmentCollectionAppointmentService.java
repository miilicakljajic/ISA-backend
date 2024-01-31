package com.isa.springboot.MediShipping.service;

import com.google.zxing.WriterException;
import com.isa.springboot.MediShipping.bean.*;
import com.isa.springboot.MediShipping.dto.*;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.mapper.EquipmentCollectionAppointmentMapper;
import com.isa.springboot.MediShipping.mapper.UserMapper;
import com.isa.springboot.MediShipping.repository.*;
import com.isa.springboot.MediShipping.util.AppointmentStatus;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private EquipmentRepository equipmentRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    MailService mailService;
    @Autowired
    AuthService authService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QrService qrService;

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
            for (EquipmentCollectionAppointment a : getAppointmentsByCompany(companyId)){
                // && a.getAdminLastname().equals(dto.getAdminLastname()
                if(a.getDate().equals(dto.date)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isTimeOverlaped(EquipmentCollectionAppointmentDto newApp,EquipmentCollectionAppointment existing){
        LocalDateTime newAppStart = newApp.getDate();
        LocalDateTime newAppEnd = newAppStart.plus(newApp.getDuration(), ChronoUnit.MINUTES);

        LocalDateTime existingStart = existing.getDate();
        LocalDateTime existingEnd = existingStart.plus(existing.getDuration(), ChronoUnit.MINUTES);

        return newAppEnd.isBefore(existingStart) && newAppStart.isAfter(existingEnd);
    }

    public List<EquipmentCollectionAppointment> findByUser(long userId)
    {
        ArrayList<EquipmentCollectionAppointment> found = new ArrayList<EquipmentCollectionAppointment>();
        for(EquipmentCollectionAppointment a : equipmentCollectionAppointmentRepository.findAll())
        {
            if(a.getUser() != null)
                if(a.getUser().getId() == userId)
                    found.add(a);
        }
        return found;
    }

    private boolean equipmentOverlap(EquipmentCollectionAppointment newApp, Company comp)
    {
        for(OrderItem item : newApp.getItems()) {
            for(Equipment eq : comp.getEquipment())
            {
                if(item.getEquipmentId() == eq.getId() && eq.getCount() < item.getCount())
                    return true;
            }
        }
        return false;
    }

    //Dobavi prvog admina koji se slobodan za taj termin, ili vrati null ako nema
    private void setAvailableAdmin(EquipmentCollectionAppointment newApp, Company company)
    {
        ArrayList<User> allAdmins = new ArrayList<User>(company.getCompanyManagers());
        ArrayList<User> busyAdmins = new ArrayList<User>();
        ArrayList<EquipmentCollectionAppointment> sameTime = new ArrayList<EquipmentCollectionAppointment>();
        for(EquipmentCollectionAppointment app : equipmentCollectionAppointmentRepository.findAll())
            if(app.getDate().equals(newApp.getDate()))
                sameTime.add(app);

        for(EquipmentCollectionAppointment app : sameTime)
            for(User admin : allAdmins)
                if(app.getAdminFirstname().equals(admin.getFirstName()) && app.getAdminLastname().equals(admin.getLastName()))
                    busyAdmins.add(admin);

        System.out.println("All admins:" + allAdmins);
        for(User potentialAdmin: allAdmins)
        {
            boolean found = false;
            for (User admin : busyAdmins)
            {
                if(admin.getFirstName().equals(potentialAdmin.getFirstName()) && admin.getLastName().equals(potentialAdmin.getLastName())) {
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                System.out.println("Found admin:" + potentialAdmin.getFirstName() + " " + potentialAdmin.getLastName());
                newApp.setAdminFirstname(potentialAdmin.getFirstName());
                newApp.setAdminLastname(potentialAdmin.getLastName());
                return;
            }
        }

        newApp.setAdminFirstname(null);
        newApp.setAdminLastname(null);
    }

    public EquipmentCollectionAppointmentDto getById(long appointmentId){
        return mapper.convertToDto(equipmentCollectionAppointmentRepository.findById(appointmentId).get());
    }

    @Transactional
    public EquipmentCollectionAppointmentDto create(long companyId,EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){

        EquipmentCollectionAppointment appointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<Company> company = companyService.getCompanyById(companyId);
        boolean isValid = isDateTimeValid(companyId,equipmentCollectionAppointmentDto.date, equipmentCollectionAppointmentDto.duration);
        boolean alreadyExists = alreadyExists(companyId,equipmentCollectionAppointmentDto);

        if(isValid && !alreadyExists && company.isPresent()) {
            appointment.setCompany(company.get());
            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment));
        }
        else{
            return null;
        }
    }

    @Transactional
    public EquipmentCollectionAppointmentDto update(EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto, long companyId){
        EquipmentCollectionAppointment updatedAppointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<EquipmentCollectionAppointment> appointment = equipmentCollectionAppointmentRepository.findById(equipmentCollectionAppointmentDto.id);
        if(appointment.isPresent()){
            if(appointment.get().getStatus() == AppointmentStatus.RESERVED){
                return null;
            }
            Optional<Company> company = companyService.getCompanyById(companyId);
            appointment.get().setAdminFirstname(updatedAppointment.getAdminFirstname());
            appointment.get().setAdminLastname(updatedAppointment.getAdminLastname());
            appointment.get().setItems(updatedAppointment.getItems());
            appointment.get().setDate(updatedAppointment.getDate());
            appointment.get().setStatus(updatedAppointment.getStatus());

            return mapper.convertToDto(equipmentCollectionAppointmentRepository.save(appointment.get()));
        }
        return  null;
    }

    public void update1(EquipmentCollectionAppointment app)
    {
        equipmentCollectionAppointmentRepository.save(app);
    }

    public ResponseDto finalizeAppointment(long companyid, long userid, EquipmentCollectionAppointmentDto equipmentCollectionAppointmentDto){
        Optional<Company> comp = companyService.getCompanyById(companyid);
        EquipmentCollectionAppointment updatedAppointment = mapper.convertToEntity(equipmentCollectionAppointmentDto);
        Optional<EquipmentCollectionAppointment> appointment = equipmentCollectionAppointmentRepository.findById(equipmentCollectionAppointmentDto.id);
        Optional<User> user = authService.getUserById(userid);
        if(user.isPresent() && user.get().getPenaltyPoints() >= 3)
            return new ResponseDto(400, "Too many penalty points!");
        if(appointment.isPresent() && user.isPresent() && comp.isPresent()){
            try {
                try {
                    appointment.get().setQr(QrService.getQRCodeImage(updatedAppointment.toString(), 300, 300));
                } catch (WriterException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(appointment.get().getStatus() != AppointmentStatus.AVAILABLE)
                    return new ResponseDto(400, "Appointment already reserved");

                if(equipmentOverlap(updatedAppointment, comp.get()))
                    return new ResponseDto(400, "Equipment reserved");
                appointment.get().setStatus(AppointmentStatus.RESERVED);
                appointment.get().setUser(user.get());
                appointment.get().setCompany(comp.get());
                appointment.get().setItems(updatedAppointment.getItems());
                //update1(appointment.get());

                equipmentCollectionAppointmentRepository.save(appointment.get());
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
       // boolean overlap = equipmentOverlap(newApp);
        boolean dateValid = isDateTimeValid(companyid,newApp.getDate(), newApp.getDuration());
        if(dateValid) {
            Optional<User> user = userRepository.findById(userid);
            Optional<Company> company = companyService.getCompanyById(companyid);

            if(user.isPresent() && user.get().getPenaltyPoints() >= 3)
                return new ResponseDto(400, "Too many penalty points!");

            if(equipmentOverlap(newApp, company.get()))
                return new ResponseDto(400, "Equipment reserved");

            if (user.isPresent() && company.isPresent()) {

                setAvailableAdmin(newApp, company.get());
                if(newApp.getAdminFirstname() == null)
                    return new ResponseDto(400, "No available admins at that time");

                //newApp = mapper.convertToEntity(create(companyid, mapper.convertToDto(newApp)));
                try {
                    newApp.setQr(QrService.getQRCodeImage(newApp.toString(), 300, 300));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                //user.get().addApointment(newApp);
                //authService.updateUser(userid, userMapper.convertToRegisterDto(user.get()));

                newApp.setCompany(company.get());
                newApp.setUser(user.get());
                //companyService.updateCompany(companyid, companyMapper.convertToCompanyDto(company.get()));
                equipmentCollectionAppointmentRepository.save(newApp);
                try {
                    mailService.sendAppointmentMail(user.get().getEmail(), newApp);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
            return new ResponseDto(200, "OK");
        }
        //if(overlap)
            //return new ResponseDto(400, "Failed: overlaps with existing appointment");
        else if(!dateValid)
            return new ResponseDto(400, "Company not available at that time");
        else
            return new ResponseDto(400,"Unknown error");
    }

    public void deleteById(long id){
        equipmentCollectionAppointmentRepository.deleteById(id);
    }

    public  List<UserAppointmentDto> getUsersWithUpcomingAppointments(long companyId){
        Optional<Company> company = companyService.getCompanyById(companyId);
        List<UserAppointmentDto> usersWithAppointments = new ArrayList<UserAppointmentDto>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        if(company.isEmpty())
            return new ArrayList<UserAppointmentDto>();
        else{

            for(EquipmentCollectionAppointment appointment : getAppointmentsByCompany(companyId)){
                if(appointment.getStatus() == AppointmentStatus.AVAILABLE) continue;

                if(checkAppointmentExpirationDate(appointment)) {
                    appointment.setStatus(AppointmentStatus.EXPIRED);
                    int currentPoints = appointment.getUser().getPenaltyPoints();
                    appointment.getUser().setPenaltyPoints(currentPoints + 2);
                    update(mapper.convertToDto(appointment),appointment.getCompany().getId());
                }

                UserAppointmentDto x = new UserAppointmentDto();
                x.setAppointmentId(appointment.getId());
                x.setFirstName(appointment.getUser().getFirstName());
                x.setLastName(appointment.getUser().getLastName());
                x.setDate(formatter.format(appointment.getDate()));
                x.setStatus(appointment.getStatus());
                usersWithAppointments.add(x);
            }

            return usersWithAppointments;
        }
    }

    public Boolean checkAppointmentExpirationDate(EquipmentCollectionAppointment appointment){
        if(appointment.getStatus() != AppointmentStatus.EXPIRED && appointment.getDate().isBefore(LocalDateTime.now())){
            return  true;
        }

        return false;
    }

    public List<EquipmentCollectionAppointment> getAppointments(long id)
    {
        return findByUser(id);
    }

    public void cancelAppointment(long id, EquipmentCollectionAppointmentDto appointment)
    {
        Optional<User> user = userRepository.findById(id);
        LocalDateTime today = LocalDateTime.now();

        if(user.isPresent())
        {
            for(EquipmentCollectionAppointment app : findByUser(id))
            {
                if(app.getId() == appointment.getId())
                {
                    for(OrderItem oi : app.getItems())
                    {
                        Equipment equipment = equipmentRepository.getById(oi.getEquipmentId());
                        equipment.setCount(equipment.getCount() + oi.getCount());
                        equipmentRepository.save(equipment);
                        orderItemRepository.delete(oi);
                    }

                    app.setStatus(AppointmentStatus.AVAILABLE);
                    app.setUser(null);
                    //userRepository.save(user.get());
                    //user.get().removeAppointment(appointmentMapper.convertToEntity(appointment));
                    update1(app);
                    if((Duration.between(today, appointment.getDate())).toHours() < 24)
                    {
                        user.get().setPenaltyPoints(user.get().getPenaltyPoints() + 2);
                    }
                    else
                    {
                        user.get().setPenaltyPoints(user.get().getPenaltyPoints() + 1);
                    }
                    userRepository.save(user.get());
                    return;
                }
            }
        }
    }
    
    public List<EquipmentCollectionAppointment> getAppointmentsByCompany(long id){
        ArrayList<EquipmentCollectionAppointment> list = new ArrayList<EquipmentCollectionAppointment>();

        for(EquipmentCollectionAppointment a : equipmentCollectionAppointmentRepository.findAll()){
            if(a.getCompany().getId() == id){
                list.add(a);
            }
        }

        return  list;
    }

    public ResponseDto approveAppointment(byte[] qr)
    {
        String appointment = qrService.readQRCode(qr);
        //EquipmentCollectionAppointment{id=0, adminFirstname='Petar', status=RESERVED, user=null}
        //String id = appointment.split("\\{",2)[1].split("=",1)[1];
        int id = extractId(appointment);
        if(id != -1)
        {
            Optional<EquipmentCollectionAppointment> app = equipmentCollectionAppointmentRepository.findById(Long.valueOf(id));
            app.get().setStatus(AppointmentStatus.DONE);
            equipmentCollectionAppointmentRepository.save(app.get());
            //todo pozvati mejl ovde
            return new ResponseDto(200, "Appointment finished!");
        }
        else
            return new ResponseDto(200, "Invalid QR");
        //pozvati djaju ovde (update)
    }

    public static int extractId(String input) {
        // Regularni izraz za izdvajanje id-a
        String regex = "id=(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Ako se podudara regularni izraz
        if (matcher.find()) {
            // Dobijamo prvi grupisani deo koji predstavlja id
            String idString = matcher.group(1);

            // Pretvaramo string u integer
            try {
                return Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing ID: " + e.getMessage());
            }
        }

        // Ako ne pronađemo id, možete vratiti neku podrazumevanu vrednost ili
        // izbaciti izuzetak u zavisnosti od vaših zahteva
        return -1;
    }
}