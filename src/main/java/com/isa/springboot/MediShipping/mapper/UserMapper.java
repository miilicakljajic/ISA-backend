package com.isa.springboot.MediShipping.mapper;

import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.LoginResultDto;
import com.isa.springboot.MediShipping.dto.RegisterDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }

    public LoginResultDto convertToUserDetailsDto(User user) {
        return modelMapper.map(user, LoginResultDto.class);
    }

<<<<<<< HEAD
=======
    public RegisterDto convertToRegisterDto(User user) { return modelMapper.map(user, RegisterDto.class);}
>>>>>>> develop
    public User convertToEntity(LoginResultDto userDetails) {
        return modelMapper.map(userDetails, User.class);
    }

    public User convertToEntity(RegisterDto register) { return modelMapper.map(register, User.class); }
}

