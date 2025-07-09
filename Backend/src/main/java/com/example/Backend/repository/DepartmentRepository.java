package com.example.Backend.repository;

import com.example.Backend.model.Department;
import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("Select m.dep from Membership m where m.user = :id")
    Department getDepId(@Param("id") User user);
}
