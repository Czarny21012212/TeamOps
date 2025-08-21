package com.example.Backend.controller;

import com.example.Backend.Dto.TaskDto;
import com.example.Backend.Dto.TaskWithUserAndDepartmentDTO;
import com.example.Backend.config.WebSocketResponse;
import com.example.Backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
    @MessageMapping("/userTasks")
    @SendTo("/topic/userTasks")
    public List<TaskDto> userTasks(@Header("user-email") String email) {
        System.out.println("email " + email);
        return taskService.getUserTasksByEmail(email);
    }



}
