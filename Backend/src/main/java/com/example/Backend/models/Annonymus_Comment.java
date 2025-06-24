package com.example.Backend.models;

import jakarta.persistence.*;
import org.w3c.dom.Text;

@Entity
public class Annonymus_Comment {
    public Annonymus_Comment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Lob
    private String content;
    private char irritation_level;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Annonymus_Comment(Long id, String title, String content, char irritation_level, Company company) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.irritation_level = irritation_level;
        this.company = company;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public char getIritation_level() {return irritation_level;}
    public void setIritation_level(char irritation_level) {this.irritation_level = irritation_level;}

    public Company getCompany() {return company;}
    public void setCompany(Company company) {this.company = company;}




}
