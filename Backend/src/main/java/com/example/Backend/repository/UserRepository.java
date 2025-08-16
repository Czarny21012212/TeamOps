package com.example.Backend.repository;

import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("Select u from User u where LOWER(u.first_name) like lower(concat('%', :firstName, '%')) And LOWER(u.last_name) like lower(concat('%', :lastName, '%')) ")
    Optional<List<User>> searchUser(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
