package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/{verifyToken}")
    public Optional<User> verify(@PathVariable String verifyToken) {
        return authService.verify(verifyToken);
    }
}
