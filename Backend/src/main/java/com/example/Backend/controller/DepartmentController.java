package com.example.Backend.controller;

import com.example.Backend.Dto.CompanyWithDepartmentDTO;

import com.example.Backend.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/createDepartment")
    public ResponseEntity<Map<String, String>> createDepartment(@RequestBody CompanyWithDepartmentDTO request) {
        return departmentService.createDepartment(request);
    }

    @GetMapping("/showAllDepartment")
    public ResponseEntity<List<Object>> showAllDepartment() {
        return departmentService.showAllDepartment();
    }

    @GetMapping("/showUserDepartment")
    public ResponseEntity<Map<String, String>> showUserDepartment() {
        return departmentService.showUserDepartment();
    }
}
