package com.example.Backend.controller;

import com.example.Backend.Dto.TaskWithUserAndDepartmentDTO;
import com.example.Backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Map<String, String>> createTask (@Valid @RequestBody TaskWithUserAndDepartmentDTO request) {
        return taskService.createTask(request);
    }

}
