package com.example.Backend.model;

import jakarta.persistence.*;

@Entity
public class UserProfile {
    public UserProfile() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int finished_tasks;
    private float difficult_level;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public UserProfile(int finished_tasks, float difficult_level, User user ) {
        this.finished_tasks = finished_tasks;
        this.difficult_level = difficult_level;
        this.user = user;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public int getFinished_tasks() {return finished_tasks;}
    public void setFinished_tasks(int finished_tasks) {this.finished_tasks = finished_tasks;}

    public void setDifficult_level(float avg_difficult_level) {
        this.difficult_level = avg_difficult_level;
    }
    public float getDifficult_level() { return difficult_level; }

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}


}
