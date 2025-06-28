package com.example.Backend.controller;

import com.example.Backend.model.User;
import com.example.Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class authenticationController {
    private final UserService userService;
    public authenticationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestPart User user, @RequestPart MultipartFile image_file) throws IOException {
        Map<String, String> response = new HashMap<>();

        return userService.register(user, image_file);
    }

}
