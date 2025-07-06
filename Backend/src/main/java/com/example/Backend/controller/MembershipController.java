package com.example.Backend.controller;

import com.example.Backend.Dto.MembershipWithDepartmentAndCompany;
import com.example.Backend.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/addUserToDepartment")
    public ResponseEntity<Map<String, String>> addUserToDepartment(@RequestBody MembershipWithDepartmentAndCompany request) {
        return membershipService.addUserToDepartment(request);
    }
}
