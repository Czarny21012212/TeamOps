package com.example.Backend.repository;

import com.example.Backend.Dto.TaskDto;
import com.example.Backend.model.Department;
import com.example.Backend.model.Task;
import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("Select m.is_leader from Membership m where m.user = :user")
    Boolean isLeader(@Param("user") User user);

    @Query("Select m.dep from Membership m where m.user = :user")
    Department getDepId(@Param("user") User user);

    @Query("Select t from Task t where t.user = :user ")
    Optional<List<TaskDto>> userTasks(@Param("user") User user);
}
