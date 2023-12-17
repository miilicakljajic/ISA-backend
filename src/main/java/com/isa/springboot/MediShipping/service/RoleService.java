package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Role;
import com.isa.springboot.MediShipping.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public Role findById(Long id) {
        Role auth = this.roleRepository.getOne(id);
        return auth;
    }

    public List<Role> findByName(String name) {
        //TODO Ovo mora u repo
        List<Role> roles = new ArrayList<Role>();
        for(Role role : this.roleRepository.findAll())
        {
            if(role.getName().equals(name))
                roles.add(role);
        }
        return roles;
    }
}
