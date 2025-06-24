package com.example.Backend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    public Company(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comapny_name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annonymus_Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();

    public Company(Long id, String comapny_name, User user) {
        this.id = id;
        this.comapny_name = comapny_name;
        this.user = user;
    }

    public Long getId() {return id;}
    public void setId(Long id) {}

    public String getComapny_name() {return comapny_name;}
    public void setComapny_name(String comapny_name) {this.comapny_name = comapny_name;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public List<Annonymus_Comment> getComments() {return comments;}
    public void setComments(List<Annonymus_Comment> comments) {this.comments = comments;}




}
