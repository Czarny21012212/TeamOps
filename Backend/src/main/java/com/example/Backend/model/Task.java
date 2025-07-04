package com.example.Backend.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    public Task() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Lob
    private String cotent;
    private char difficult_levels;
    private Date date;
    private String status;
    private boolean is_read;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = false)
    private Department dep;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskProblem> task_problems = new ArrayList<>();

    public Task(String title, String cotent, char difficult_levels, Date date, String status, boolean is_read, User user, Department dep) {
        this.title = title;
        this.cotent = cotent;
        this.difficult_levels = difficult_levels;
        this.date = date;
        this.status = status;
        this.is_read = is_read;
        this.user = user;
        this.dep = dep;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getCotent() {return cotent;}
    public void setCotent(String cotent) {this.cotent = cotent;}

    public char getDifficult_levels() {return difficult_levels;}
    public void setDifficult_levels(char difficult_levels) {this.difficult_levels = difficult_levels;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public boolean isIs_read() {return is_read;}
    public void setIs_read(boolean is_read) {this.is_read = is_read;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Department getDep() {return dep;}
    public void setDep(Department dep) {this.dep = dep;}

    public List<TaskProblem> getTask_problems() {return task_problems;}
    public void setTask_problems(List<TaskProblem> task_problems) {}
}
