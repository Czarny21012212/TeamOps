package com.example.Backend.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
public class Membership {
    public Membership() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String position;
    private Date date;
    private boolean is_leader;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = false)
    private Department dep;

    public Membership(String position, Date date, boolean is_leader, User user, Department dep) {
        this.position = position;
        this.date = date;
        this.is_leader = is_leader;
        this.user = user;
        this.dep = dep;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getPosition() {return position;}
    public void setPosition(String position) {this.position = position;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public boolean isIs_leader() {return is_leader;}
    public void setIs_leader(boolean is_leader) {this.is_leader = is_leader;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Department getDep() {return dep;}
    public void setDep(Department dep) {this.dep = dep;}

}
