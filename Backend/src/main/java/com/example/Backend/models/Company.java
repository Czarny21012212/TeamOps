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
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annonymus_Comment> comments = new ArrayList<>();

    public Company(Long id, String comapny_name, User user) {
        this.id = id;
        this.comapny_name = comapny_name;
        this.user = user;
    }


}
