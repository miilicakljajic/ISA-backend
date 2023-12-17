package com.isa.springboot.MediShipping.dto;

import com.isa.springboot.MediShipping.bean.Role;

import java.util.List;

public class LoginResultDto {
    private Long id;
    private List<Role> roles;
    private UserTokenState tokenState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserTokenState getTokenState() {
        return tokenState;
    }

    public void setTokenState(UserTokenState tokenState) {
        this.tokenState = tokenState;
    }
}
