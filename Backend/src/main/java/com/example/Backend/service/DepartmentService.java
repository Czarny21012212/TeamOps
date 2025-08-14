package com.example.Backend.service;

import com.example.Backend.Dto.CompanyWithDepartmentDTO;
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

    public ResponseEntity<Map<String, String>> createDepartment(CompanyWithDepartmentDTO request){
        Map<String, String> response = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            response.put("message", "Unauthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try{

            System.out.println(request.getCompanyId() + " " + request.getDepName());

            User user = userRepository.findByEmail(authentication.getName()).get();

            Optional<Long> checkOwnerCompany = companyRepository.checkOwnerCompany(user);

            if(checkOwnerCompany.isEmpty()){
                response.put("message", "You are not owner");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if(request.getCompanyId() == null || request.getDepName() == null){
                response.put("status", "create a company first");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Department department = new Department();
            department.setCompany(companyRepository.findById(request.getCompanyId()).get());
            department.setDep_name(request.getDepName());
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

            Optional<List<Department>> departmentsCheck = companyRepository.showAllDepartments(companyId);

            if(departmentsCheck.isEmpty()){
                response.put("message", "Department list is empty");
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            List<Department> departments = departmentsCheck.get();

            System.out.println(departments.size());
            for(Department department : departments){
                Map<String, String> map = new HashMap<>();
                map.put("depName", department.getDep_name());
                map.put("depId", String.valueOf(department.getId()));
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

            Optional<User> userCheck = userRepository.findByEmail(auth.getName());

            if (userCheck.isEmpty()) {
                response.put("message", "You are not logged in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            User user = userCheck.get();

            Optional<Department> departmentCheck = membershipRepository.showUserDepartment(user);
            if(departmentCheck.isEmpty()){
                response.put("message", "Department list is empty");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Department department = departmentCheck.get();

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
