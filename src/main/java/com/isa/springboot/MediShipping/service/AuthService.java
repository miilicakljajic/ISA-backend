package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    public Optional<User> verify(String verifyToken) {
        Optional<User> user = userService.getUserByVerifyToken(verifyToken);
        if(user.isPresent()) {
            user.get().setVerified(true);
            userService.updateUser(user.get().getId(), user.get());
            return user;
        }

        return Optional.empty();
    }
}
