package com.example.Backend.Dto;

import com.example.Backend.model.User;
import jakarta.persistence.Lob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MessageDTO {
    private final String title;
    @Lob
    private final String content;
    private final Date date;
    private int count_of_read;
    private List<Long> recipient = new ArrayList<>();

    public MessageDTO(String title, String content, Date date, List<Long> recipient) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.recipient = recipient;
    }

    public String getTitle() {return title;}
    public String getContent() {return content;}
    public Date getDate() {return date;}
    public List<Long> getRecipient() {return recipient;}



}
