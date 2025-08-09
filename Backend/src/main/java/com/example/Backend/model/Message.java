package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Message {
    public Message() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Lob
    private String content;
    private Date date;
    private int count_of_read;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", nullable = false)
    private User owner;

    @ManyToMany(mappedBy = "messages", cascade = CascadeType.ALL)
    private List<User> recipient = new ArrayList<>();


    public Message(String title, String content, Date date, int count_of_read, User owner, List<User> recipient) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.count_of_read = count_of_read;
        this.owner = owner;
        this.recipient = recipient;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public int getCount_of_read() {return count_of_read;}
    public void setCount_of_read(int count_of_read) {this.count_of_read = count_of_read;}

    public User getOwner() {return owner;}
    public void setOwner(User owner) {this.owner = owner;}

    public List<User> getRecipient() {return recipient;}
    public void setRecipient(List<User> recipient) {this.recipient = recipient;}
}
