package com.example.Backend.controller;

import com.example.Backend.Dto.UserProfileDTO;
import com.example.Backend.model.UserProfile;
import com.example.Backend.service.UserProfileService;
import com.example.Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final UserService userService;

    public UserProfileController(UserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @PostMapping("/updateUserProfile")
    public ResponseEntity<Map<String, String>> updateUserProfile(@RequestBody UserProfileDTO requset) {
        return userProfileService.updateUserProfile(requset);
    }
    @GetMapping("/showUsersFromTeam/{depId}")
    public ResponseEntity<List<Map<String, String>>> showUsersFromTeam(@PathVariable String depId) {
        return userService.showUsersFromTeam(Long.valueOf(depId));
    }


}
