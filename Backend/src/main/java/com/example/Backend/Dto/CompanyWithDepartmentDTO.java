package com.example.Backend.Dto;

import com.example.Backend.model.Department;

public class CompanyWithDepartmentDTO {

    private Long companyId;
    private String depName;

    public Long getCompanyId() {return companyId;}
    public void setCompanyId(Long companyId) {this.companyId = companyId;}

   public String getDepName() {return depName;}
    public void setDepName(String depName) {this.depName = depName;}
}
