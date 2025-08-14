package com.example.Backend.repository;

import com.example.Backend.model.Company;
import com.example.Backend.model.Department;
import com.example.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("Select c.departments from Company c where c.id = :companyId")
    List<Department> showAllDepartments(@Param("companyId") Long companyId);

    @Query("Select c.id from Company c where c.user = :user")
    Long findCompanyIdByUserID(@Param("user") User user);

    @Query("Select m.company from Membership m where m.user = :user" )
    Optional<Company> showComapny(@Param("user") User user);

    @Query("Select c.id from Company c where c.user = :user")
    Optional<Long> findYourCompanyId(@Param("user") User user);

}
