package com.example.Backend.service;

import com.example.Backend.model.User;
import com.example.Backend.model.UserProfile;
import com.example.Backend.repository.UserProfileRepository;
import com.example.Backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
   private final UserRepository user_repository;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   AuthenticationManager authenticationManager;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserService(UserRepository user_repository, PasswordEncoder passwordEncoder) {
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

         UserProfile userProfile = new UserProfile();
         userProfile.setUser(user);
         userProfile.setFinished_tasks(0);
         userProfile.setDifficult_level(0);
         userProfileRepository.save(userProfile);

        response.put("message", "success");
     }catch(Exception e){
        response.put("message", "error: " + e.getMessage());
     }

     return new ResponseEntity<>(response, HttpStatus.OK);
   }

   public ResponseEntity<Map<String, String>> login(User user, HttpServletRequest request) {
       Map<String, String> response = new HashMap<>();

       try{
           Authentication authentication =
                   authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

           SecurityContextHolder.getContext().setAuthentication(authentication);

           HttpSession session = request.getSession();
           session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

           response.put("message", "success");

       }catch(Exception e) {
           response.put("message", "error" + e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
       return new ResponseEntity<>(response, HttpStatus.OK);

   }
}
