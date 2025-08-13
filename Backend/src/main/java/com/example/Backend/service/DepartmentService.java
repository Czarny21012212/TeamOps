package com.example.Backend.service;

import com.example.Backend.model.Company;
import com.example.Backend.model.Department;
import com.example.Backend.model.Statistics;
import com.example.Backend.model.User;
import com.example.Backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentService;
    private final DepartmentRepository departmentRepository;
    private final StatisticsRepository statisticsRepository;
    private final MembershipRepository membershipRepository;

    public DepartmentService(UserRepository userRepository, CompanyRepository companyRepository, DepartmentRepository departmentService, DepartmentRepository departmentRepository, StatisticsRepository statisticsRepository, MembershipRepository membershipRepository, MembershipRepository membershipRepository1) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
        this.statisticsRepository = statisticsRepository;
        this.membershipRepository = membershipRepository1;
    }

    public ResponseEntity<Map<String, String>> createDepartment(Department department, Long company_id){
        Map<String, String> response = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try{
            String email = authentication.getName();
            User admin = userRepository.findByEmail(email).get();

            if(!Objects.equals(admin.getId(), company_id)){
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }

            department.setCompany(companyRepository.findById(company_id).get());
            departmentRepository.save(department);

            Statistics statistics = new Statistics();
            statistics.setDep(department);
            statistics.setUnderstand(0);
            statistics.setDifficulty(0);
            statistics.setClarity(0);
            statistics.setTime_spent(0);
            statistics.setFinished_tasks(0);
            statistics.setProblematic_finished_tasks(0);
            statisticsRepository.save(statistics);

            response.put("message", "Department created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Object>> showAllDepartment(){
        List<Object> list = new ArrayList<>();
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "You are not logged in");
                return new ResponseEntity<>(list, HttpStatus.UNAUTHORIZED);
            }
            User user = userRepository.findByEmail(auth.getName()).get();

            Long companyId = companyRepository.findCompanyIdByUserID(user);

            List<Department> departments = companyRepository.showAllDepartments(companyId);

            System.out.println(departments.size());
            for(Department department : departments){
                Map<String, String> map = new HashMap<>();
                map.put("depName", department.getDep_name());
                map.put("depId", String.valueOf(department.getId()));
                System.out.println(department.getDep_name());
                list.add(map);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", e.getMessage());
            list.add(response);
            return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, String>> showUserDepartment(){
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "Please login!");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findByEmail(auth.getName()).get();

            Department department = membershipRepository.showUserDepartment(user);

            response.put("message", "Department details");
            response.put("depId", String.valueOf(department.getId()));
            response.put("depName", department.getDep_name());

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
