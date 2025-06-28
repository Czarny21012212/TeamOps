package com.example.Backend.service;

import com.example.Backend.model.User;
import com.example.Backend.repository.User_Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
   private final User_Repository user_repository;

   public UserService(User_Repository user_repository) {
      this.user_repository = user_repository;
   }

   public ResponseEntity<Map<String, String>> register(User user, MultipartFile image_file) throws IOException {
      Map<String, String> response = new HashMap<>();

     try{
        user.setPhoto_name(image_file.getOriginalFilename());
        user.setPhoto_type(image_file.getContentType());
        user.setPhoto_date(image_file.getBytes());
        user_repository.save(user);

        response.put("message", "success");
     }catch(Exception e){
        response.put("message", "error" + e.getMessage());
     }

     return new ResponseEntity<>(response, HttpStatus.OK);
   }
}
