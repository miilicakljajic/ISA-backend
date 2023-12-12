package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Role;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.LoginDto;
import com.isa.springboot.MediShipping.dto.RegisterDto;
import com.isa.springboot.MediShipping.dto.LoginResultDto;
import com.isa.springboot.MediShipping.dto.UserTokenState;
import com.isa.springboot.MediShipping.mapper.UserMapper;
import com.isa.springboot.MediShipping.repository.UserRepository;
import com.isa.springboot.MediShipping.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    private HashMap<String, Long> verifyTokens = new HashMap<String, Long>();
    private final UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;
    UUID uuid = UUID.randomUUID();

    @Autowired
    public AuthService(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    // Create a new user
    public Optional<User> createUser(RegisterDto userdto) {
        User user = new User();
        user.setEmail(userdto.getEmail());
        user.setPassword(passwordEncoder.encode(userdto.getPassword()));
        user.setFirstName(userdto.getFirstName());
        user.setLastName(userdto.getLastName());
        user.setCity(userdto.getCity());
        user.setCountry(userdto.getCountry());
        user.setPhoneNumber(userdto.getPhoneNumber());
        user.setOccupation(userdto.getOccupation());
        user.setCompanyInfo(userdto.getCompanyInfo());
        user.setPictureLink(userdto.getPictureLink());
        user.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        user.setEnabled(false);

        String verifyToken = uuid.toString();
        List<Role> roles = roleService.findByName("ROLE_USER");
        user.setRoles(roles);
        if(GetUserByEmail(user.getEmail()).equals(Optional.empty())) {
            Optional<User> newUser = Optional.of(userRepository.save(user));
            verifyTokens.put(verifyToken, newUser.get().getId());
            mailService.sendAuthMail(user.getEmail(), verifyToken);
            return newUser;
        }
        return Optional.empty();
    }

    public Optional<User> verify(String verifyToken) {
        Optional<User> user = getUserByVerifyToken(verifyToken);
        if(user.isPresent()) {
            user.get().setEnabled(true);
            userService.updateUser(user.get().getId(), user.get());
            return user;
        }

        return Optional.empty();
    }

    public Optional<User> getUserByVerifyToken(String verifyToken) {
        return getUserById(verifyTokens.get(verifyToken));
    }

    public LoginResultDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        LoginResultDto details = new LoginResultDto();
        details = userMapper.convertToUserDetailsDto(user);
        details.setTokenState(new UserTokenState(jwt,expiresIn));
        return  details;
    }

    public Optional<User> GetUserByEmail(String email) {
        for(User user : getAllUsers()) {
            if(user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
