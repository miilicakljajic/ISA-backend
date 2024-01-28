package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.CompanyDto;
import com.isa.springboot.MediShipping.dto.UserAppointmentDto;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.repository.EquipmentCollectionAppointmentRepository;
import com.isa.springboot.MediShipping.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Company createCompany(CompanyDto companyDto) {
        Company company = mapper.convertToEntity(companyDto);
        for(User u: company.getCompanyManagers()) {
            u.setRoles(roleService.findByName("ROLE_COMPANY_ADMIN"));
            u.setEnabled(true);
            u.setPassword(passwordEncoder.encode(u.getPassword()));
        }
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() { return companyRepository.findAll(); }

    public Optional<Company> getCompanyById(Long id) { return companyRepository.findById(id); }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Optional<Company> existingCompany = getCompanyById(id);
        Company updatedCompany = mapper.convertToEntity(companyDto);

        if(existingCompany.isPresent()) {
            existingCompany.get().setName(updatedCompany.getName());
            existingCompany.get().setAddress(updatedCompany.getAddress());
            existingCompany.get().setDescription(updatedCompany.getDescription());
            existingCompany.get().setAverageRating(updatedCompany.getAverageRating());
            existingCompany.get().setAllAppointments(updatedCompany.getAllAppointments());
            existingCompany.get().setEquipment(updatedCompany.getEquipment());
            existingCompany.get().setCompanyManagers(updatedCompany.getCompanyManagers());

            for (EquipmentCollectionAppointment a : existingCompany.get().getAllAppointments()) {
                a.setCompany(updatedCompany);
            }

            return mapper.convertToCompanyDto(companyRepository.save(existingCompany.get()));
        }
        return null;
    }
    public void deleteAllCompanies() { companyRepository.deleteAll(); }

    public void deleteCompany(Long id) { companyRepository.deleteById(id); }

    public List<EquipmentCollectionAppointment> getAppointmentsByCompany(long id){
        Optional<Company> company = getCompanyById(id);
        if(company.isEmpty())
            return new ArrayList<EquipmentCollectionAppointment>();
        else
            return company.get().getAllAppointments().stream().toList();
    }

    public Company getByManagerId(long id){
        for(Company c : getAllCompanies()){
            for(User u : c.getCompanyManagers()){
                if(u.getId() == id){
                    return c;
                }
            }
        }

        return null;
    }

}