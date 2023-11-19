package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) { return companyRepository.save(company); }

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
