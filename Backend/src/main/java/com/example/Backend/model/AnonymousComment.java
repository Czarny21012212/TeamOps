package com.example.Backend.model;

import jakarta.persistence.*;

@Entity
public class AnonymousComment {
    public AnonymousComment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Lob
    private String content;
    private int irritation_level;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public AnonymousComment(String title, String content, int irritation_level) {
        this.title = title;
        this.content = content;
        this.irritation_level = irritation_level;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public int getIrritation_level() {return irritation_level;}
    public void setIrritation_level(int irritation_level) {this.irritation_level = irritation_level;}

    public Company getCompany() {return company;}
    public void setCompany(Company company) {this.company = company;}




}
