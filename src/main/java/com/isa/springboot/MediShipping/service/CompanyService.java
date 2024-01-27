package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.CompanyDto;
import com.isa.springboot.MediShipping.dto.UserAppointmentDto;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import com.isa.springboot.MediShipping.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private UserService userService;

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
        Optional<Company> company = companyRepository.findById(id);
        Company companyDetails = mapper.convertToEntity(companyDto);
        if(company.isPresent()) {
            Company existingCompany = company.get();
            existingCompany.setName(companyDetails.getName());
            existingCompany.setAddress(companyDetails.getAddress());
            existingCompany.setDescription(companyDetails.getDescription());
            existingCompany.setAverageRating(companyDetails.getAverageRating());
            existingCompany.setAllAppointments(companyDetails.getAllAppointments());
            existingCompany.setEquipment(companyDetails.getEquipment());
            existingCompany.setCompanyManagers(companyDetails.getCompanyManagers());

            return mapper.convertToCompanyDto(companyRepository.save(existingCompany));
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

}