package com.example.Backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Company company;

    public User(Long id, String email, String password, String first_name, String last_name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getFirstName() {return first_name;}
    public void setFirstName(String firstName) {this.first_name = firstName;}

    public String getLastName() {return last_name;}
    public void setLastName(String lastName) {this.last_name = lastName;}

}
