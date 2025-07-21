package com.example.Backend.model;

import jakarta.persistence.*;

@Entity
public class MessageUser {
    public MessageUser() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id", nullable = false)
    private Inbox inbox;

   public MessageUser(User user, Inbox inbox) {
       this.user = user;
       this.inbox = inbox;
   }

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Inbox getInbox() {return inbox;}
    public void setInbox(Inbox inbox) {this.inbox = inbox;}


}
