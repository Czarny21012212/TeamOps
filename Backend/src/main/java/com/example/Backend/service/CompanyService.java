package com.example.Backend.service;

import com.example.Backend.model.Company;
import com.example.Backend.model.User;
import com.example.Backend.repository.CompanyRepository;
import com.example.Backend.repository.MembershipRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;


    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository, MembershipRepository membershipRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public ResponseEntity<Map<String, String>> createCompany(Company company){
        Map<String, String> response = new HashMap<>();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            System.out.println(auth.getName());
            if(auth == null){
                response.put("message", "Error");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findByEmail(auth.getName()).get();

            company.setUser(user);
            companyRepository.save(company);
            response.put("message", "Company created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, String>> showCompany(){
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "Please Log in!");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findByEmail(auth.getName()).get();
            Optional<Company> companyCheck = companyRepository.showComapny(user);

            if(companyCheck.isEmpty()){
                response.put("message", "You are not in any company");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Company company = companyCheck.get();

            response.put("companyName", company.getCompany_name());
            response.put("companyId", String.valueOf(company.getId()));
            response.put("companyDescription", company.getCompany_description());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, String>> ShowYourCompany(){
        Map<String, String> response = new HashMap<>();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth == null){
                response.put("message", "Please Log in!");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            Optional<User> userCheck = userRepository.findByEmail(auth.getName());
            if(userCheck.isEmpty()){
                response.put("message", "You are not in any company");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            User user = userCheck.get();
            Optional<Long> companyIdCheck = companyRepository.findYourCompanyId(user);

            if(companyIdCheck.isEmpty()){
                response.put("message", "You are not in any company");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Long companyId = companyIdCheck.get();

            Optional<Company> companyCheck = companyRepository.findById(companyId);
            if(companyCheck.isEmpty()){
                response.put("message", "You are not in any company");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Company company = companyCheck.get();

            response.put("companyName", company.getCompany_name());
            response.put("companyId", String.valueOf(company.getId()));
            response.put("companyDescription", company.getCompany_description());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}












