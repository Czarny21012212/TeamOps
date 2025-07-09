package com.example.Backend.controller;

import com.example.Backend.Dto.UserProfileDTO;
import com.example.Backend.model.UserProfile;
import com.example.Backend.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/updateUserProfile")
    public ResponseEntity<Map<String, String>> updateUserProfile(@RequestBody UserProfileDTO requset) {
        return userProfileService.updateUserProfile(requset);
    }
}
