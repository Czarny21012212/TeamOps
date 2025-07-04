package com.example.Backend.service;

import com.example.Backend.model.Company;
import com.example.Backend.model.User;
import com.example.Backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Map<String, String> createCompany(User user, Company company){

    }
}
