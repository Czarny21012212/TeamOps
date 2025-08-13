package com.example.Backend.controller;

import com.example.Backend.Dto.CompanyWithUserDTO;
import com.example.Backend.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/showCompany")
    public ResponseEntity<Map<String, String>> showCompany() {
        return companyService.showCompany();
    }
}
