package com.example.Backend.service;

import com.example.Backend.Dto.TaskProblemDTO;
import com.example.Backend.model.Department;
import com.example.Backend.model.Task;
import com.example.Backend.model.TaskProblem;
import com.example.Backend.model.User;
import com.example.Backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskProblemService {
    private final TaskProblemRepository taskProblemRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;
    private final MembershipService membershipService;
    private final MembershipRepository membershipRepository;

    public TaskProblemService(TaskProblemRepository taskProblemRepository, UserRepository userRepository, DepartmentRepository departmentRepository, TaskRepository taskRepository, MembershipService membershipService, MembershipRepository membershipRepository) {
        this.taskProblemRepository = taskProblemRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
        this.membershipService = membershipService;
        this.membershipRepository = membershipRepository;
    }

    public ResponseEntity<Map<String, String>> createTaskProblem(TaskProblemDTO request) {
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User employee = userRepository.findByEmail(auth.getName()).get();

            if(employee == null){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Date date = new Date();
            Department dep = membershipRepository.getDepId(employee);
            Task task = taskRepository.findById(request.getTask_id()).get();

            TaskProblem taskProblem = new TaskProblem();
            taskProblem.setTitle(request.getTitle());
            taskProblem.setContent(request.getContent());
            taskProblem.setUser(employee);
            taskProblem.setDate(date);
            taskProblem.setTask(task);
            taskProblem.setDep(dep);
            taskProblemRepository.save(taskProblem);

            response.put("message", "Task problem created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
