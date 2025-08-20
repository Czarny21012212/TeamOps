package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class TaskProblem {
    public TaskProblem() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = false)
    private Department dep;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TaskProblem(String title, String content, Task task) {
        this.title = title;
        this.content = content;
        this.task = task;
    }
    public int getId() {return id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public Department getDep() {return dep;}
    public void setDep(Department dep) {this.dep = dep;}

    public Task getTask() {return task;}
    public void setTask(Task task) {this.task = task;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
