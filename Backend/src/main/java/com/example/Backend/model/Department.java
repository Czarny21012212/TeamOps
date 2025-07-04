package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    public Department() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dep_name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToOne(mappedBy = "dep", cascade = CascadeType.ALL, orphanRemoval = true)
    private Statistics statistics;

    @OneToMany(mappedBy = "dep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "dep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> task = new ArrayList<>();

    @OneToMany(mappedBy = "dep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskProblem> taskProblems = new ArrayList<>();


    public Department(String dep_name) {
        this.dep_name = dep_name;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getDep_name() {return dep_name;}
    public void setDep_name(String dep_name) {this.dep_name = dep_name;}

    public Company getCompany() {return company;}
    public void setCompany(Company company) {this.company = company;}

    public Statistics getStatistics() {return statistics;}
    public void setStatistics(Statistics statistics) {this.statistics = statistics;}

    public List<Membership> getMemberships() {return memberships;}
    public void setMemberships(List<Membership> memberships) {this.memberships = memberships;}

    public List<Task> getTask() {return task;}
    public void setTask(List<Task> task) {this.task = task;}

    public List<TaskProblem> getTaskProblems() {return taskProblems;}
    public void setTaskProblems(List<TaskProblem> taskProblems) {this.taskProblems = taskProblems;}


}
