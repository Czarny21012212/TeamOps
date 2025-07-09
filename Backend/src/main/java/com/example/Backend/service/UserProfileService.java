package com.example.Backend.service;

import com.example.Backend.repository.UserProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public ResponseEntity<Map<String, String>> updateUserProfile(){
        Map<String, String> response = new HashMap<>();


    }
}
