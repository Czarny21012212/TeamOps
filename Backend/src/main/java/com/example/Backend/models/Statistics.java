package com.example.Backend.models;

import jakarta.persistence.*;

@Entity
public class Statistics {
    public Statistics() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int finished_tasks;
    private int problematic_finished_tasks;
    private float avg_difficulty;
    private float avg_understand;
    private float avg_clarity;
    private float avg_time_spent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id", nullable = false)
    private Department dep;

    public Statistics(Long id, int finished_tasks, int problematic_finished_tasks, float avg_difficulty, float avg_understand, float avg_clarity, float avg_time_spent, Department dep) {
        this.id = id;
        this.finished_tasks = finished_tasks;
        this.problematic_finished_tasks = problematic_finished_tasks;
        this.avg_difficulty = avg_difficulty;
        this.avg_understand = avg_understand;
        this.avg_clarity = avg_clarity;
        this.avg_time_spent = avg_time_spent;
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

    public float getAvg_difficulty() {return avg_difficulty;}
    public void setAvg_difficulty(float avg_difficulty) {
        this.avg_difficulty = avg_difficulty;
    }


    public float getAvg_understand() {return avg_understand;}
    public void setAvg_understand(float avg_understand) {this.avg_understand = avg_understand;}

    public float getAvg_clarity() {return avg_clarity;}
    public void setAvg_clarity(float avg_clarity) {this.avg_clarity = avg_clarity;}

    public float getAvg_time_spent() {return avg_time_spent;}
    public void setAvg_time_spent(float avg_time_spent) {this.avg_time_spent = avg_time_spent;}

    public Department getDep() {return dep;}
    public void setDep(Department dep) {this.dep = dep;}

}

