package com.example.Backend.repository;

import com.example.Backend.model.Membership;
import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("Select c.id from Company c where c.user = :id")
    Long findCompanyIdByUserID(@Param("id") User user);

}
