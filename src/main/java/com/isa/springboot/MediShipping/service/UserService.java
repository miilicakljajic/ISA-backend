package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.LoginDto;
import com.isa.springboot.MediShipping.repository.UserRepository;
import net.bytebuddy.build.BuildLogger;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    private HashMap<String, Long> verifyTokens = new HashMap<String, Long>();
    UUID uuid = UUID.randomUUID();

    // Create a new user
    public Optional<User> createUser(User user) {
        user.setVerified(false);
        String verifyToken = uuid.toString();
        if(GetUserByEmail(user.getEmail()).equals(Optional.empty())) {
            Optional<User> newUser = Optional.of(userRepository.save(user));
            verifyTokens.put(verifyToken, newUser.get().getId());
            mailService.sendAuthMail(user.getEmail(), verifyToken);
            return newUser;
        }
        return Optional.empty();
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> GetUserByEmail(String email) {
        for(User user : getAllUsers()) {
            if(user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByVerifyToken(String verifyToken) {
        return getUserById(verifyTokens.get(verifyToken));
    }

    public Optional<User> login(LoginDto dto) {
        for(User user : getAllUsers()) {
            if(user.getEmail().equals(dto.getEmail()) && user.getPassword().equals(dto.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setCity(userDetails.getCity());
            existingUser.setCountry(userDetails.getCountry());
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            existingUser.setOccupation(userDetails.getOccupation());
            existingUser.setCompanyInfo(userDetails.getCompanyInfo());
            existingUser.setPictureLink(userDetails.getPictureLink());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Other business logic related to users
}