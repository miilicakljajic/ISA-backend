package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public Company createCompany(@RequestBody Company company) { return companyService.createCompany(company); }

    @GetMapping
    public List<Company> getAllCompanies() { return companyService.getAllCompanies(); }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) { return companyService.getCompanyById(id); }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        return companyService.updateCompany(id, companyDetails);
    }

    @DeleteMapping
    public String deleteAllCompanies() {
        companyService.deleteAllCompanies();
        return "All companies have been deleted successfully!";
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) { companyService.deleteCompany(id); }
}