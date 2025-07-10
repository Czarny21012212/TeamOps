package com.example.Backend.service;

import com.example.Backend.model.Department;
import com.example.Backend.model.Statistics;
import com.example.Backend.model.User;
import com.example.Backend.repository.CompanyRepository;
import com.example.Backend.repository.DepartmentRepository;
import com.example.Backend.repository.StatisticsRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class DepartmentService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentService;
    private final DepartmentRepository departmentRepository;
    private final StatisticsRepository statisticsRepository;

    public DepartmentService(UserRepository userRepository, CompanyRepository companyRepository, DepartmentRepository departmentService, DepartmentRepository departmentRepository, StatisticsRepository statisticsRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
        this.statisticsRepository = statisticsRepository;
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
}
