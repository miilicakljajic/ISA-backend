package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.LoginDto;
import com.isa.springboot.MediShipping.dto.RegisterDto;
import com.isa.springboot.MediShipping.dto.LoginResultDto;
import com.isa.springboot.MediShipping.service.AuthService;
import com.isa.springboot.MediShipping.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/{verifyToken}")
    public Optional<User> verify(@PathVariable String verifyToken) {
        return authService.verify(verifyToken);
    }

    // Create a new user
    @PostMapping(value = "/register")
    public Optional<User> createUser(@RequestBody RegisterDto user) { return authService.createUser(user); }

    @PostMapping("/login")
    public LoginResultDto login(@RequestBody LoginDto dto) {
        return authService.login(dto);
    }
}
