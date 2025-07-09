package com.example.Backend.repository;

import com.example.Backend.model.Department;
import com.example.Backend.model.Task;
import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("Select m.is_leader from Membership m where m.user = :id")
    Boolean isLeader(@Param("id") User user);

    @Query("Select m.dep from Membership m where m.user = :id")
    Department getDepId(@Param("id") User user);
}
