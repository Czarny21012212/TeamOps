package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    public Company(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String company_name;
    private String company_description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnonymousComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships = new ArrayList<>();

    public Company(String company_name, String company_description) {
        this.company_name = company_name;
        this.company_description = company_description;
    }

    public Long getId() {return id;}
    public void setId(Long id) {}

    public String getCompany_name() {return company_name;}
    public void setCompany_name(String company_name) {this.company_name = company_name;}

    public String getCompany_description() {return company_description;}
    public void setCompany_description(String company_description) {this.company_description = company_description;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public List<AnonymousComment> getComments() {return comments;}
    public void setComments(List<AnonymousComment> comments) {this.comments = comments;}
}
