package com.example.Backend.Dto;

import jakarta.persistence.Lob;

public class AnonymousCommentDTO {
    private String title;
    @Lob
    private String content;
    private int irritation_level;

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public int getIrritation_level() {
        return irritation_level;
    }
}
