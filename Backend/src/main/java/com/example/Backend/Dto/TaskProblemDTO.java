package com.example.Backend.Dto;

import jakarta.persistence.Lob;

public class TaskProblemDTO {
    private String title;
    @Lob
    private String content;
    private Long task_id;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getTask_id() {
        return task_id;
    }

}
