package com.example.Backend.controller;

import com.example.Backend.Dto.CompanyWithDepartmentDTO;

import com.example.Backend.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final SimpMessagingTemplate messagingTemplate;

    public DepartmentController(DepartmentService departmentService, SimpMessagingTemplate messagingTemplate) {
        this.departmentService = departmentService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/createDepartment")
    public ResponseEntity<Map<String, String>> createDepartment(@Valid @RequestBody CompanyWithDepartmentDTO request) {
        return departmentService.createDepartment(request);
    }

    @GetMapping("/showAllDepartments")
    public ResponseEntity<List<Object>> showAllDepartments() {
        return departmentService.showAllDepartment();
    }

    @GetMapping("/showUserDepartment")
    public ResponseEntity<Map<String, String>> showUserDepartment() {
        return departmentService.showUserDepartment();
    }

    @GetMapping("/isLeader/{depId}")
    public ResponseEntity<Boolean> isLeader(@Valid @PathVariable Long depId) {
        return departmentService.isLeader(depId);
    }
}
