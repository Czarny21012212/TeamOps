package com.example.Backend.model;

import jakarta.persistence.*;

@Entity
public class Statistics {
    public Statistics() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int finished_tasks;
    private int problematic_finished_tasks;
    private int difficulty;
    private int understand;
    private int clarity;
    private int time_spent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = false)
    private Department dep;

    public Statistics(int finished_tasks, int problematic_finished_tasks, int difficulty, int understand, int clarity, int time_spent, Department dep) {
        this.finished_tasks = finished_tasks;
        this.problematic_finished_tasks = problematic_finished_tasks;
        this.difficulty = difficulty;
        this.understand = understand;
        this.clarity = clarity;
        this.time_spent = time_spent;
        this.dep = dep;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public int getFinished_tasks() {return finished_tasks;}
    public void setFinished_tasks(int finished_tasks) {this.finished_tasks = finished_tasks;}

    public int getProblematic_finished_tasks() {return problematic_finished_tasks;}
    public void setProblematic_finished_tasks(int problematic_finished_tasks) {
        this.problematic_finished_tasks = problematic_finished_tasks;
    }

    public int getDifficulty() {return difficulty;}
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    public int getUnderstand() {return understand;}
    public void setUnderstand(int understand) {this.understand = understand;}

    public int getClarity() {return clarity;}
    public void setClarity(int clarity) {this.clarity = clarity;}

    public int getTime_spent() {return time_spent;}
    public void setTime_spent(int time_spent) {this.time_spent = time_spent;}

    public Department getDep() {return dep;}
    public void setDep(Department dep) {this.dep = dep;}

}

