package com.example.Backend.service;

import com.example.Backend.Dto.AnonymousCommentDTO;
import com.example.Backend.model.AnonymousComment;
import com.example.Backend.model.Company;
import com.example.Backend.model.User;
import com.example.Backend.repository.AnonymousCommentRepository;
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
public class AnonymousCommentService {

    private final AnonymousCommentRepository anonymousCommentRepository;
    private final UserRepository userRepository;

    public AnonymousCommentService(AnonymousCommentRepository anonymousCommentRepository, UserRepository userRepository) {
        this.anonymousCommentRepository = anonymousCommentRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, String>> createAnonumousComment(AnonymousCommentDTO request) {
        Map<String, String> response = new HashMap<>();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<User> testEmployee = userRepository.findByEmail(auth.getName());

            if(testEmployee.isEmpty()){
                response.put("message ","You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User employee = testEmployee.get();

            Company company = employee.getMembership().getCompany();

            if(company == null){
                response.put("message ","You have to be in a company");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            System.out.println(request.getIrritation_level());

            AnonymousComment anonymousComment = new AnonymousComment();
            anonymousComment.setCompany(company);
            anonymousComment.setContent(request.getContent());
            anonymousComment.setTitle(request.getTitle());
            anonymousComment.setIrritation_level(request.getIrritation_level());
            anonymousCommentRepository.save(anonymousComment);

            response.put("message", "success");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
