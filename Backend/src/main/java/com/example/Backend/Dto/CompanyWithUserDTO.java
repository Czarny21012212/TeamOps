package com.example.Backend.Dto;

import com.example.Backend.model.Company;
import com.example.Backend.model.User;

public class CompanyWithUserDTO {
    private Company company;

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

}
