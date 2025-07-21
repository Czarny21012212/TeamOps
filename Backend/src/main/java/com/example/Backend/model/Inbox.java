package com.example.Backend.model;

import jakarta.persistence.*;
import org.aspectj.bridge.Message;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Inbox {
    public Inbox() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Lob
    private String content;
    private Date date;
    private boolean is_read;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", nullable = false)
    private User owner;

    @ManyToMany(mappedBy = "inbox", cascade = CascadeType.ALL)
    private List<User> recipient = new ArrayList<User>();


    public Inbox(String title, String content, Date date, boolean is_read, User owner, List<User> recipient) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.is_read = is_read;
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

    public boolean isIs_read() {return is_read;}
    public void setIs_read(boolean is_read) {this.is_read = is_read;}

    public User getOwner() {return owner;}
    public void setOwner(User owner) {this.owner = owner;}

    public List<User> getRecipient() {return recipient;}
    public void setRecipient(List<User> recipient) {this.recipient = recipient;}
}
