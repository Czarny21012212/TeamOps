package com.example.Backend.model;

import jakarta.persistence.*;

import java.sql.Date;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;


    public Inbox(String title, String content, Date date, boolean is_read, User owner, User recipient) {
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

    public User getRecipient() {return recipient;}
    public void setRecipient(User recipient) {this.recipient = recipient;}
}
