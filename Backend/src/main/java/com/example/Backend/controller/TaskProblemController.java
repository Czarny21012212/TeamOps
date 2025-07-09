package com.example.Backend.controller;

import com.example.Backend.Dto.TaskProblemDTO;
import com.example.Backend.model.TaskProblem;
import com.example.Backend.service.TaskProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskProblemController {
    private final TaskProblemService taskProblemService;

    public TaskProblemController(TaskProblemService taskProblemService) {
        this.taskProblemService = taskProblemService;
    }

    @PostMapping("/createTaskProblem")
    public ResponseEntity<Map<String, String>> createTaskProblem(@RequestBody TaskProblemDTO request) {
        return taskProblemService.createTaskProblem(request);
    }
}
