package com.google.sps.servlets;

public class Comment {
    public String username ="";
    public String text = "";
    public long timestamp = 0;

    public Comment(String username, String comment, long timestamp) {
        this.username = username;
        this.text = comment;
        this.timestamp = timestamp;
    }
}
