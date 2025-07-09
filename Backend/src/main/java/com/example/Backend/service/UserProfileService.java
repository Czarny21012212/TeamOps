package com.example.Backend.service;

import com.example.Backend.Dto.UserProfileDTO;
import com.example.Backend.model.User;
import com.example.Backend.model.UserProfile;
import com.example.Backend.repository.UserProfileRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, String>> updateUserProfile(UserProfileDTO request){
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User employee = userRepository.findByEmail(auth.getName()).get();

            if(employee == null){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            UserProfile userProfile = employee.getUser_profile();
            userProfile.setDifficult_level(userProfile.getDifficult_level() + request.getDifficult_level());
            userProfile.setFinished_tasks(userProfile.getFinished_tasks() + 1);
            userProfileRepository.save(userProfile);

            response.put("message", "User profile updated");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
