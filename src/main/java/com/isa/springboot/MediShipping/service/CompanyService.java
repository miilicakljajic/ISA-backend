package com.isa.springboot.MediShipping.service;

import com.isa.springboot.MediShipping.bean.Company;
import com.isa.springboot.MediShipping.bean.Equipment;
import com.isa.springboot.MediShipping.bean.User;
import com.isa.springboot.MediShipping.dto.CompanyDto;
import com.isa.springboot.MediShipping.dto.ContractDto;
import com.isa.springboot.MediShipping.mapper.CompanyMapper;
import com.isa.springboot.MediShipping.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional
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
    private RestTemplate restTemplate;

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
            existingCompany.get().setEquipment(updatedCompany.getEquipment());
            existingCompany.get().setCompanyManagers(updatedCompany.getCompanyManagers());

            return mapper.convertToCompanyDto(companyRepository.save(existingCompany.get()));
        }
        return null;
    }

    public Company updateCompanyRegular(Long id, Company company) {
        Optional<Company> existingCompany = getCompanyById(id);

        if(existingCompany.isPresent()) {
            existingCompany.get().setName(company.getName());
            existingCompany.get().setAddress(company.getAddress());
            existingCompany.get().setDescription(company.getDescription());
            existingCompany.get().setAverageRating(company.getAverageRating());
            existingCompany.get().setEquipment(company.getEquipment());
            existingCompany.get().setCompanyManagers(company.getCompanyManagers());

            return companyRepository.save(company);
        }
        return null;
    }

    public void deleteAllCompanies() { companyRepository.deleteAll(); }

    public void deleteCompany(Long id) { companyRepository.deleteById(id); }


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

    public ContractDto canDeliver(ContractDto contractDto){
        Optional<Company> company = getCompanyById(contractDto.getCompanyId());
        Integer avaliableItemCounter = 0;
        if(company.isPresent()) {
            for (String s : contractDto.getItems()) {
                String eqName = s.split(";")[0];
                Integer eqQuantity = Integer.parseInt(s.split(";")[1]);
                for (Equipment e : company.get().getEquipment()) {
                    if (e.getName().equals(eqName) && e.getCount() >= eqQuantity) {
                        avaliableItemCounter++;
                    }
                }
            }

            if(avaliableItemCounter == contractDto.getItems().size()){
                contractDto.setCanDeliver(true);
                ContractService.update(contractDto);
            }else{
                String methodUrl = "http://localhost:4337/api/producer/notify";
                sendMessage("Equipment cannot be delivered",methodUrl);
            }
        }
        return contractDto;
    }

    public void sendMessage(String message,String methodUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity with the data and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(message, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(methodUrl, requestEntity, String.class);

        // Process the response as needed
        String responseData = responseEntity.getBody();
    }
    public boolean sendEquipment(ContractDto contractDto) {
        Optional<Company> company = getCompanyById(contractDto.getCompanyId());
        if(company.isPresent()) {
            contractDto = canDeliver(contractDto);
            if(contractDto.getCanDeliver() == true) {
                String methodUrl = "http://localhost:4337/api/producer/notify";
                String poruka = "Your order will be arriving to";
                sendMessage(poruka, methodUrl);

                return true;
            }
        }
        return false;
    }
}