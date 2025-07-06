package com.example.Backend.service;

import com.example.Backend.Dto.MembershipWithDepartmentAndCompany;
import com.example.Backend.model.Company;
import com.example.Backend.model.Department;
import com.example.Backend.model.Membership;
import com.example.Backend.model.User;
import com.example.Backend.repository.CompanyRepository;
import com.example.Backend.repository.DepartmentRepository;
import com.example.Backend.repository.MembershipRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    public MembershipService(MembershipRepository membershipRepository, UserRepository userRepository, CompanyRepository companyRepository, DepartmentRepository departmentRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<Map<String, String>> addUserToDepartment(MembershipWithDepartmentAndCompany request) {
        Map<String, String> response = new HashMap<>();
        try{
            java.util.Date date = new java.util.Date();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "You are not logged in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            User user = userRepository.findByEmail(auth.getName()).get();
            Long company_id = membershipRepository.findCompanyIdByUserID(user);


            Membership membership = new Membership();
            membership.setDate(date);
            System.out.println(date);
            membership.setIs_leader(request.isIs_leader());
            User employee = userRepository.findById(request.getUser_id()).get();
            membership.setUser(employee);
            membership.setPosition(request.getPosition());
            Company company = companyRepository.findById(company_id).get();
            membership.setCompany(company);
            Department department = departmentRepository.findById(request.getDep_id()).get();
            membership.setDep(department);
            membershipRepository.save(membership);

            response.put("message", "Membership added");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
