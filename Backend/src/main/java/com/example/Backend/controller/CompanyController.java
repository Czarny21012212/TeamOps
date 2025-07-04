package com.example.Backend.controller;

import com.example.Backend.Dto.CompanyWithUserDTO;
import com.example.Backend.model.Company;
import com.example.Backend.model.User;
import com.example.Backend.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/createCompany")
    public ResponseEntity<Map<String, String>> createCompany(@RequestBody CompanyWithUserDTO request) {
        return companyService.createCompany(request.getCompany());
    }
}
