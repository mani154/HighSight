package com.HighSight.HighSightBackend.repo.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "highlights")
public class Highlight {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Integer pageId;
    private String content;
    private String tag;

    public Highlight() {}
    public Highlight(Integer pageId, String content, String tag) {
        this.pageId = pageId;
        this.content = content;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPageId() {
        return pageId;
    }

    public String getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }
}
