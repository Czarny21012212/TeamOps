package com.example.Backend.repository;

import com.example.Backend.model.TaskProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProblemRepository extends JpaRepository<TaskProblem, Long> {
}
