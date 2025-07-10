package com.example.Backend.service;

import com.example.Backend.Dto.StatisticsDTO;
import com.example.Backend.model.Statistics;
import com.example.Backend.model.User;
import com.example.Backend.repository.StatisticsRepository;
import com.example.Backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final UserRepository userRepository;

    public StatisticsService(StatisticsRepository statisticsRepository, UserRepository userRepository) {
        this.statisticsRepository = statisticsRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, String>> updateStatistics(StatisticsDTO request) {
        Map<String, String> response = new HashMap<>();

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<User> testUser = userRepository.findByEmail(auth.getName());
            if(testUser.isEmpty()){
                response.put("message", "You have to log in");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            User employee = testUser.get();
            Statistics statistics = employee.getMembership().getDep().getStatistics();
            statistics.setFinished_tasks(statistics.getFinished_tasks() + 1);
            statistics.setDifficulty(statistics.getDifficulty() + request.getDifficulty());
            statistics.setClarity(statistics.getClarity() + request.getClarity());
            statistics.setTime_spent(statistics.getTime_spent() + request.getTime_spent());
            statistics.setUnderstand(statistics.getUnderstand() + request.getUnderstand());
            statisticsRepository.save(statistics);

            response.put("message", "Statistics updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
