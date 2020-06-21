package com.HighSight.HighSightBackend.controller.model;


public class AddHighlightRequest {
    private String url;
    private String content;
    private boolean useful;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUseful(boolean useful) {
        this.useful = useful;
    }

    public String getUrl() {
        return url;
    }
    public String getContent() {
        return content;
    }
    public boolean  isUseful() {
        return useful;
    }
}
