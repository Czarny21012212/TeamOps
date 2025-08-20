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
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("Select m.dep from Membership m where m.user = :user")
    Optional<Department> showUserDepartment(@Param("user") User user);

    @Query("Select m.dep from Membership m where m.user = :user")
    Department getDepId(@Param("user") User user);

    @Query("Select m.user from Membership m where m.dep = :dep")
    List<User> showUsersFromDep(@Param("dep") Department dep);

    @Query("select m.company from Membership m where m.user = :user")
    Optional<Company> findCompanyIdByUserID(@Param("user") User user);

    @Query("Select m.is_leader from Membership m where m.user = :user And m.company = :company And m.dep = :dep")
    Optional<Boolean> isLeader(@Param("user") User user, @Param("company") Company company,@Param("dep") Department dep);

}
