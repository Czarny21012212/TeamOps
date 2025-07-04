package com.example.Backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompanyController {
    public ResponseEntity<Map<String, String>> createCompany() {
        Map<String, String> response = new HashMap<>();

    }
}
