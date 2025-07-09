package com.example.Backend.service;

import com.example.Backend.Dto.TaskWithUserAndDepartmentDTO;
import com.example.Backend.model.Department;
import com.example.Backend.model.Task;
import com.example.Backend.model.User;
import com.example.Backend.repository.TaskRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, String >> createTask(TaskWithUserAndDepartmentDTO request){
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "you have to log in first");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User leader = userRepository.findByEmail(auth.getName()).get();

            if(!taskRepository.isLeader(leader)){
                response.put("message", "you are not the leader");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            Date date  = new Date();
            Department dep = taskRepository.getDepId(leader);
            User employee = userRepository.findById(request.getUser_id()).get();

            Task task = new Task();
            task.setDate(date);
            task.setTitle(request.getTitle());
            task.setCotent(request.getContent());
            task.setDep(dep);
            task.setDifficult_levels(request.getDifficult_levels());
            task.setIs_read(false);
            task.setUser(employee);
            task.setStatus("Waiting");
            taskRepository.save(task);

            response.put("message", "task created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
