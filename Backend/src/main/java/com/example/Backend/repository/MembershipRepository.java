package com.example.Backend.repository;

import com.example.Backend.model.Company;
import com.example.Backend.model.Department;
import com.example.Backend.model.Membership;
import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("Select m.dep from Membership m where m.user = :user")
    Department showUserDepartment(@Param("user") User user);

    @Query("Select m.dep from Membership m where m.user = :user")
    Department getDepId(@Param("user") User user);

    @Query("Select m.user from Membership m where m.dep = :dep")
    List<User> showUsersFromDep(@Param("dep") Department dep);

}
