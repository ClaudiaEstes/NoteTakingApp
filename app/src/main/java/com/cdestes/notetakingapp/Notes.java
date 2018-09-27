package com.cdestes.notetakingapp;

public class Notes {
    private String title;
    private String content;

    public Notes(){
    }

    public Notes(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
