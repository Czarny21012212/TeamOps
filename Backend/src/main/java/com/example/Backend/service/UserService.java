package com.example.Backend.service;

import com.example.Backend.model.User;
import com.example.Backend.repository.User_Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
   private final User_Repository user_repository;
   private final PasswordEncoder passwordEncoder;

   public UserService(User_Repository user_repository, PasswordEncoder passwordEncoder) {
      this.user_repository = user_repository;
       this.passwordEncoder = passwordEncoder;
   }

   public ResponseEntity<Map<String, String>> register(User user) {
       Map<String, String> response = new HashMap<>();

     try{
         Optional<User> check = user_repository.findByEmail(user.getEmail());

         if(check.isPresent()) {
             response.put("message", "Something went wrong");
             return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
         }

         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user_repository.save(user);

        response.put("message", "success");
     }catch(Exception e){
        response.put("message", "error" + e.getMessage());
     }

     return new ResponseEntity<>(response, HttpStatus.OK);
   }


}
