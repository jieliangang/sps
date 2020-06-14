package com.google.sps.servlets;

/** Comment describes comment posted on the portfolio website including username and published date**/
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
