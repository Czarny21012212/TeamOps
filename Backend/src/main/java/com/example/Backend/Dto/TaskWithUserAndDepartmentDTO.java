package com.example.Backend.Dto;

import jakarta.persistence.Lob;
import java.util.Date;

public class TaskWithUserAndDepartmentDTO {
    private String title;
    @Lob
    private String content;
    private char difficult_levels;
    private Long user_id;

    public String getTitle() {
        return title;
    }
    @Lob
    public String getContent() {
        return content;
    }
    public char getDifficult_levels() {
        return difficult_levels;
    }
    public Long getUser_id() {
        return user_id;
    }

}
