package com.example.Backend.controller;

import com.example.Backend.config.JWTService;
import com.example.Backend.model.User;
import com.example.Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class authenticationController {
    private final UserService userService;
    private final JWTService jwtService;

    public authenticationController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register( @RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) throws NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();
        String token = jwtService.generateJWT(user.getEmail());
        map.put("token", token);
        return ResponseEntity.ok(map);

    }

}
