package com.example.Backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    @ColumnDefault("null")
    private String photo_name;
    @ColumnDefault("null")
    private String photo_type;
    @Lob
    @ColumnDefault("null")
    private byte[] photo_date;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Company company;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inbox> inbox_owner = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<Inbox> inbox_recipient = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private User_Profile user_profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> task = new ArrayList<>();

    public User( String email, String password, String first_name, String last_name, String photo_name, String photo_type, byte[] photo_date) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.photo_name = photo_name;
        this.photo_type = photo_type;
        this.photo_date = photo_date;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getFirstName() {return first_name;}
    public void setFirstName(String firstName) {this.first_name = firstName;}

    public String getLastName() {return last_name;}
    public void setLastName(String lastName) {this.last_name = lastName;}

    public String getPhoto_name() {return photo_name;}
    public void setPhoto_name(String photoName) {this.photo_name = photoName;}

    public Company getCompany() {return company;}
    public void setCompany(Company company) {this.company = company;}

    public List<Inbox> getInbox_owner() {return inbox_owner;}
    public void setInbox_owner(List<Inbox> inbox_owner) {
        this.inbox_owner = inbox_owner;
    }

    public List<Inbox> getInbox_recipient() {return inbox_recipient;}
    public void setInbox_recipient(List<Inbox> inbox_recipient) {
        this.inbox_recipient = inbox_recipient;
    }

    public User_Profile getUser_profile() {return user_profile;}
    public void setUser_profile(User_Profile user_profile) {this.user_profile = user_profile;}

    public List<Task> getTask() {return task;}
    public void setTask(List<Task> task) {this.task = task;}

    public String getPhoto_type() {return photo_type;}
    public void setPhoto_type(String photo_type) {this.photo_type = photo_type;}

    public byte[] getPhoto_date() {return photo_date;}
    public void setPhoto_date(byte[] photo_date) {this.photo_date = photo_date;}
}
