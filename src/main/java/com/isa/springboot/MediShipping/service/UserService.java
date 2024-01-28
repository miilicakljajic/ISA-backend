package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.OrderItem;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.EquipmentCollectionAppointmentDto;
import com.isa.springboot.MediShipping.dto.LoginDto;
import com.isa.springboot.MediShipping.mapper.EquipmentCollectionAppointmentMapper;
import com.isa.springboot.MediShipping.mapper.UserMapper;
import com.isa.springboot.MediShipping.repository.EquipmentRepository;
import com.isa.springboot.MediShipping.repository.OrderItemRepository;
import com.isa.springboot.MediShipping.repository.UserRepository;
import com.isa.springboot.MediShipping.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EquipmentCollectionAppointmentMapper appointmentMapper;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = GetUserByEmail(email);
        if( user.isEmpty())
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        else
            return user.get();
    }

    public Optional<User> GetUserByEmail(String email) {
        for(User user : getAllUsers()) {
            if(user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    public List<EquipmentCollectionAppointment> getAppointments(long id)
    {
        Optional<User> user = getUserById(id);
        if(user.isPresent())
            return user.get().getAppointments().stream().toList();
        return new ArrayList<EquipmentCollectionAppointment>();
    }

    public void cancelAppointment(long id, EquipmentCollectionAppointmentDto appointment)
    {
        Optional<User> user = getUserById(id);
        LocalDateTime today = LocalDateTime.now();

        if(user.isPresent())
        {
            for(EquipmentCollectionAppointment app : user.get().getAppointments())
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
                    userRepository.save(user.get());
                    user.get().removeAppointment(appointmentMapper.convertToEntity(appointment));
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

    public void update(User updatedUser){
        Optional<User> currentUser = getUserById(updatedUser.getId());

        if(currentUser.isPresent()){
            currentUser.get().setAppointments(updatedUser.getAppointments());
            currentUser.get().setPenaltyPoints(updatedUser.getPenaltyPoints());

            userRepository.save(currentUser.get());
        }
    }
}