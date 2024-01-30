package com.isa.springboot.MediShipping.controller;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import com.isa.springboot.MediShipping.dto.CompanyDto;
import com.isa.springboot.MediShipping.dto.ContractDto;
import com.isa.springboot.MediShipping.dto.UserAppointmentDto;
import com.isa.springboot.MediShipping.service.AuthService;
import com.isa.springboot.MediShipping.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @PostMapping
    public Company createCompany(@RequestBody CompanyDto companydto)
    {
        return companyService.createCompany(companydto);
    }
    @GetMapping
    public List<Company> getAllCompanies() { return companyService.getAllCompanies(); }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) { return companyService.getCompanyById(id); }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDetails) {
        return companyService.updateCompany(id, companyDetails);
    }
    @DeleteMapping
    public String deleteAllCompanies() {
        companyService.deleteAllCompanies();
        return "All companies have been deleted successfully!";
    }
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) { companyService.deleteCompany(id); }

    @GetMapping("/manager/{id}")
    public Company getByManagerId(@PathVariable long id){
        return  companyService.getByManagerId(id);
    }

    @PostMapping("/sendEquipment")
    public boolean sendEquipment(@RequestBody ContractDto contractDto){
         return companyService.sendEquipment(contractDto);
    }
    @PostMapping("/canDeliver")
    public ContractDto canDeliver(@RequestBody ContractDto contractDto){
        return  companyService.canDeliver(contractDto);
    }
}
