package com.example.Backend.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Membership {
    public Membership() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String position;
    private java.util.Date date;
    private boolean is_leader;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = true, unique = true)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = true)
    private Department dep;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    public Membership(String position, Date date, boolean is_leader) {
        this.position = position;
        this.date = date;
        this.is_leader = is_leader;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getPosition() {return position;}
    public void setPosition(String position) {this.position = position;}

    public java.util.Date getDate() {return date;}
    public void setDate(java.util.Date date) {this.date = date;}

    public boolean isIs_leader() {return is_leader;}
    public void setIs_leader(boolean is_leader) {this.is_leader = is_leader;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Department getDep() {return dep;}
    public void setDep(Department dep) {this.dep = dep;}

    public Company getCompany() {return company;}
    public void setCompany(Company company) {this.company = company;}

}
