package com.example.Backend.service;

import com.example.Backend.Dto.UserSearchDTO;
import com.example.Backend.model.Department;
import com.example.Backend.model.Membership;
import com.example.Backend.model.User;
import com.example.Backend.model.UserProfile;
import com.example.Backend.repository.DepartmentRepository;
import com.example.Backend.repository.MembershipRepository;
import com.example.Backend.repository.UserProfileRepository;
import com.example.Backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
   private final UserRepository user_repository;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   AuthenticationManager authenticationManager;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public UserService(UserRepository user_repository, PasswordEncoder passwordEncoder) {
      this.user_repository = user_repository;
       this.passwordEncoder = passwordEncoder;
   }

   public ResponseEntity<Map<String, String>> register(User user) {
       Map<String, String> response = new HashMap<>();

     try{
         Optional<User> check = user_repository.findByEmail(user.getEmail());

         if(check.isPresent()) {
             response.put("message", "Something went wrong");
             return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
         }

         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user_repository.save(user);

         UserProfile userProfile = new UserProfile();
         userProfile.setUser(user);
         userProfile.setFinished_tasks(0);
         userProfile.setDifficult_level(0);
         userProfileRepository.save(userProfile);

         Membership membership = new Membership();
         membership.setUser(user);
         membership.setPosition("null");
         membership.setCompany(null);
         membership.setDate(null);
         membership.setIs_leader(false);
         membership.setDep(null);
         membershipRepository.save(membership);

        response.put("message", "success");
     }catch(Exception e){
        response.put("message", "error: " + e.getMessage());
     }

     return new ResponseEntity<>(response, HttpStatus.OK);
   }

   public ResponseEntity<Map<String, String>> login(User user, HttpServletRequest request) {
       Map<String, String> response = new HashMap<>();

       try{
           Authentication authentication =
                   authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

           SecurityContextHolder.getContext().setAuthentication(authentication);

           HttpSession session = request.getSession();
           session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

           response.put("message", "success");

       }catch(Exception e) {
           response.put("message", "error" + e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
       return new ResponseEntity<>(response, HttpStatus.OK);
   }

   public ResponseEntity<Map<String, String>> dataAboutUser(Authentication auth){
        Map<String, String> response = new HashMap<>();

       User user = userRepository.findByEmail(auth.getName()).get();

        if(user == null) {
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        return new ResponseEntity<>(response, HttpStatus.OK);
   }
   public ResponseEntity<List<Map<String, String>>> showUsersFromTeam(Long depId) {
        List<Map<String, String>> response = new ArrayList<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if(depId == null){
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Optional<Department> depCheck = departmentRepository.findById(depId);

            if(depCheck.isEmpty()){
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Department dep = depCheck.get();

            List<User> users = membershipRepository.showUsersFromDep(dep);

            for(User employee : users){
                Map<String, String> map = new HashMap<>();
                map.put("email", employee.getEmail());
                map.put("firstName", employee.getFirstName());
                map.put("lastName", employee.getLastName());
                map.put("finishedTasks", userProfileRepository.showFinishedTasks(employee));
                response.add(map);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

   }
    public ResponseEntity<List<Object>> searchUser(UserSearchDTO request){
        List<Object> list = new ArrayList<>();
        Map<String, String> response = new HashMap<>();

        try{

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth == null){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Optional<List<User>> usersCheck = user_repository.searchUser(request.getFirstName(), request.getLastName());
            if(usersCheck.isEmpty()){
                response.put("message", "User not found");
                list.add(response);
                return new ResponseEntity<>(list, HttpStatus.OK);
            }


            for(User users : usersCheck.get()){
                Map<String, String> map = new HashMap<>();
                map.put("firstName", users.getFirstName());
                map.put("lastName", users.getLastName());
                map.put("userId", String.valueOf(users.getId()));
                list.add(map);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);

        }catch(Exception e){
            response.put("message", "error" + e.getMessage());
            list.add(response);
            return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
