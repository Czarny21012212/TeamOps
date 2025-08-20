package com.example.Backend.Dto;
import com.example.Backend.model.Department;
import com.example.Backend.model.User;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;

import java.util.Date;

public class TaskDto {
    private Long id;
    private char difficultLevels;
    private String title;
    private Date date;
    private Long departmentId;
    private Long userId;
    private String status;
    private String content;

    public TaskDto(Long id, char difficultLevels, String title, Date date, Long departmentId, Long userId, String status, String content) {
        this.id = id;
        this.difficultLevels = difficultLevels;
        this.title = title;
        this.date = date;
        this.departmentId = departmentId;
        this.userId = userId;
        this.status = status;
        this.content = content;
    }

    public Long getId() { return id; }
    public int getDifficultLevels() { return difficultLevels; }
    public String getTitle() { return title; }
    public Date getDate() { return date; }
    public Long getDepartmentId() { return departmentId; }
    public Long getUserId() { return userId; }
    public String getStatus() { return status; }
    public String getContent() { return content; }

}
