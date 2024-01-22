package com.isa.springboot.MediShipping.controller;
import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.LoginDto;
import com.isa.springboot.MediShipping.service.CompanyService;
import com.isa.springboot.MediShipping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/byid/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Update user by ID

<<<<<<< HEAD
=======
    @GetMapping("/appointments/{id}")
    public List<EquipmentCollectionAppointment> getAppointments(@PathVariable Long id) {
        return userService.getAppointments(id);
    }
>>>>>>> develop

    // Delete all users
    @DeleteMapping
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "All users have been deleted successfully.";
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}