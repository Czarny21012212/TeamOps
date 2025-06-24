package com.example.Backend.models;

import jakarta.persistence.*;

@Entity
public class User_Profile {
    public User_Profile() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int finished_tasks;
    private float avg_difficult_level;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public User_Profile(Long id, int finished_tasks, float avg_difficult_level, User user ) {
        this.id = id;
        this.finished_tasks = finished_tasks;
        this.avg_difficult_level = avg_difficult_level;
        this.user = user;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public int getFinished_tasks() {return finished_tasks;}
    public void setFinished_tasks(int finished_tasks) {this.finished_tasks = finished_tasks;}

    public void setAvg_difficult_level(float avg_difficult_level) {
        this.avg_difficult_level = avg_difficult_level;
    }
    public float getAvg_difficult_level() { return avg_difficult_level; }

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}


}
