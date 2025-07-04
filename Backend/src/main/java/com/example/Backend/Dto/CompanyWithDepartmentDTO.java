package com.example.Backend.Dto;

import com.example.Backend.model.Department;

public class CompanyWithDepartmentDTO {

    private Long company_id;
    private Department department;

    public Long getCompany_id() {return company_id;}
    public void setCompany_id(Long company_id) {this.company_id = company_id;}

    public Department getDepartment() {return department;}
    public void setDepartment(Department department) {this.department = department;}
}
