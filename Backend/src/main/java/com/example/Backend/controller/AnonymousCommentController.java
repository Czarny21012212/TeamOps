package com.example.Backend.controller;

import com.example.Backend.Dto.AnonymousCommentDTO;
import com.example.Backend.service.AnonymousCommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnonymousCommentController {

    private final AnonymousCommentService anonymousCommentService;

    public AnonymousCommentController(AnonymousCommentService anonymousCommentService) {
        this.anonymousCommentService = anonymousCommentService;
    }

    @PostMapping("/createAnonymousComments")
    public ResponseEntity<Map<String, String>> createAnonymousComments(@Valid @RequestBody AnonymousCommentDTO request){
        return anonymousCommentService.createAnonumousComment(request);
    }
}
