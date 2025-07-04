package com.example.Backend.controller;

import com.example.Backend.Dto.CompanyWithDepartmentDTO;
import com.example.Backend.model.Department;
import com.example.Backend.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService1;

    public DepartmentController(DepartmentService departmentService1) {
        this.departmentService1 = departmentService1;
    }

    @PostMapping("/createDepartment")
    public ResponseEntity<Map<String, String>> createDepartment(@RequestBody CompanyWithDepartmentDTO request) {
        return departmentService1.createDepartment(request.getDepartment(), request.getCompany_id());
    }
}
