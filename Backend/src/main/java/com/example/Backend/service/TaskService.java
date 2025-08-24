package com.example.Backend.service;

import com.example.Backend.Dto.TaskDto;
import com.example.Backend.Dto.TaskWithUserAndDepartmentDTO;
import com.example.Backend.config.WebSocketResponse;
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

import java.util.*;

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
            Optional<Department> checkDep = taskRepository.getDepId(leader);
            if(checkDep.isEmpty()){
                response.put("message", "you are not the leader");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            Department dep = checkDep.get();
            User employee = userRepository.findById(request.getUser_id()).get();

            Task task = new Task();
            task.setDate(date);
            task.setTitle(request.getTitle());
            task.setContent(request.getContent());
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

    public List<TaskDto> getUserTasksByEmail(String email) {
        Optional<User> checkUser = userRepository.findByEmail(email);
        if (checkUser.isEmpty()) return new ArrayList<>();
        User user = checkUser.get();
        System.out.println(user.getEmail());

        Optional<List<Task>> checkTasks = taskRepository.userTasks(user);
        if (checkTasks.isEmpty() || checkTasks.get().isEmpty()) return new ArrayList<>();
        List<Task> tasks = checkTasks.get();

        List<TaskDto> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(new TaskDto(
                    task.getId(),
                    task.getDifficult_levels(),
                    task.getTitle(),
                    task.getDate(),
                    task.getDep() != null ? task.getDep().getId() : null,
                    task.getUser() != null ? task.getUser().getId() : null,
                    task.getStatus(),
                    task.getContent()
            ));
        }
        return result;
    }

    public List<TaskDto> getUsertasksSentByEmail(String email){
        Optional<User> checkUser = userRepository.findByEmail(email);
        if (checkUser.isEmpty()) return new ArrayList<>();
        User user = checkUser.get();

        Optional<Department> checkUserDep = taskRepository.getDepId(user);
        if(checkUserDep.isEmpty()) return new ArrayList<>();
        Department userDep = checkUserDep.get();

        Optional<List<Task>> checkDepTasks = taskRepository.depTasks(userDep);
        if (checkDepTasks.isEmpty()) return new ArrayList<>();
        List<Task> tasks = checkDepTasks.get();

        List<TaskDto> result = new ArrayList<>();

        for (Task task : tasks) {
            result.add(new TaskDto(
                    task.getId(),
                    task.getDifficult_levels(),
                    task.getTitle(),
                    task.getDate(),
                    task.getDep() != null ? task.getDep().getId() : null,
                    task.getUser() != null ? task.getUser().getId() : null,
                    task.getStatus(),
                    task.getContent()
            ));
        }
        return result;
    }

    public ResponseEntity<Map<String, String>> getTask(Long task_id){
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth == null){
                response.put("message", "you have to log in first");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            Optional<User> checkUser = userRepository.findByEmail(auth.getName());
            if(checkUser.isEmpty()){
                response.put("message", "you have to log in first");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<Task> checkTask = taskRepository.findById(task_id);
            if(checkTask.isEmpty()){
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Task task = checkTask.get();
            task.setIs_read(true);
            taskRepository.save(task);

            response.put("id", String.valueOf(task.getId()));
            response.put("date", String.valueOf(task.getDate()));
            response.put("Content", String.valueOf(task.getContent()));
            response.put("difficultLevel", String.valueOf(task.getDifficult_levels()));
            response.put("status", String.valueOf(task.getStatus()));
            response.put("title", String.valueOf(task.getTitle()));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
















