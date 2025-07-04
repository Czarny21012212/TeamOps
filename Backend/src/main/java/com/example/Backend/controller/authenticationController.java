package com.example.Backend.controller;

import com.example.Backend.model.User;
import com.example.Backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class authenticationController {
    private final UserService userService;

    public authenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register( @RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletRequest request) throws NoSuchAlgorithmException {
        return userService.login(user, request);
    }

}
