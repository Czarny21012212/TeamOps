package com.example.Backend.controller;

import com.example.Backend.Dto.CompanyWithUserDTO;
import com.example.Backend.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/createCompany")
    public ResponseEntity<Map<String, String>> createCompany(@Valid @RequestBody CompanyWithUserDTO request) {
        return companyService.createCompany(request.getCompany());
    }

    @GetMapping("/showCompany")
    public ResponseEntity<Map<String, String>> showCompany() {
        return companyService.showCompany();
    }

    @GetMapping("/showYourCompany")
    public ResponseEntity<Map<String, String>> showYourCompany() {return companyService.ShowYourCompany();}
}
