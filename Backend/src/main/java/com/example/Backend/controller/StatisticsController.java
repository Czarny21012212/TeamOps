package com.example.Backend.controller;

import com.example.Backend.Dto.StatisticsDTO;
import com.example.Backend.service.StatisticsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/updateStatistics")
    public ResponseEntity<Map<String, String>> updateStatistics(@Valid @RequestBody StatisticsDTO request) {
        return statisticsService.updateStatistics(request);
    }
}
