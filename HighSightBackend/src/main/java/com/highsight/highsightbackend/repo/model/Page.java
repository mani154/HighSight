package com.HighSight.HighSightBackend.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;
    private String overallScore;

    public Page() {}
    public Page(String url, String overallScore) {
        this.url = url;
        this.overallScore = overallScore;
    }

    public Page(Integer id, String url, String overallScore) {
        this.id = id;
        this.url = url;
        this.overallScore = overallScore;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getOverallScore() {
        return overallScore;
    }
}

