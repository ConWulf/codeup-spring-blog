package com.codeup.springblog.model;

import java.util.List;

public class Post {
    private long id;
    private String title;
    private String body;
    private List<String> tags;

    public Post() { }


    public Post(long id, String title, String body, List<String> tags) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    public Post(String title, String body, List<String> tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
