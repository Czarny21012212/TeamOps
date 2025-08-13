package com.example.Backend.repository;

import com.example.Backend.model.User;
import com.example.Backend.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("Select up.finished_tasks from UserProfile up where up.user = :user")
    String showFinishedTasks(@Param("user") User user);
}
