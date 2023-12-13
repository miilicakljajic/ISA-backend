package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Role;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.CompanyDto;
import com.isa.springboot.MediShipping.dto.RegisterDto;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.mapper.UserMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyMapper mapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Company createCompany(CompanyDto companydto) {
        Company company = mapper.convertToEntity(companydto);
        for(User u: company.getCompanyManagers()) {
            u.setRoles(roleService.findByName("ROLE_COMPANY_ADMIN"));
            u.setEnabled(true);
            u.setPassword(passwordEncoder.encode(u.getPassword()));
        }
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() { return companyRepository.findAll(); }

    public Optional<Company> getCompanyById(Long id) { return companyRepository.findById(id); }

    public Company updateCompany(Long id, Company companyDetails) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()) {
            Company existingCompany = company.get();
            existingCompany.setName(companyDetails.getName());
            existingCompany.setAddress(companyDetails.getAddress());
            existingCompany.setDescription(companyDetails.getDescription());
            existingCompany.setAverageRating(companyDetails.getAverageRating());
            existingCompany.setAllAppointments(companyDetails.getAllAppointments());
            existingCompany.setEquipment(companyDetails.getEquipment());
            existingCompany.setCompanyManagers(companyDetails.getCompanyManagers());
            return companyRepository.save(existingCompany);
        }
        return null;
    }

    public void deleteAllCompanies() { companyRepository.deleteAll(); }

    public void deleteCompany(Long id) { companyRepository.deleteById(id); }

    public Company findUsingManagerId(long id){
        for(Company c : getAllCompanies()){
            for(User u : c.getCompanyManagers()){
                if(u.getId() == id){
                    return  c;
                }
            }
        }
        return  null;
    }
}
