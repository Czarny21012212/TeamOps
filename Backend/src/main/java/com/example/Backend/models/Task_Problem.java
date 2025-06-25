package com.example.Backend.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Task_Problem {
    public Task_Problem() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    @Lob
    private String content;
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = false)
    private Department dep;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Task_Problem( String title, String content, Date date, Department dep, Task task) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.dep = dep;
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
}
