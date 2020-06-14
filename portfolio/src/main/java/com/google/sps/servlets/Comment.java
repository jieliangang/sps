package com.google.sps.servlets;

/** Comment describes comment posted on the portfolio website including username and published date**/
public final class Comment {
    private final String username;
    private final String text;
    private final long timestamp;

    public Comment(String username, String comment, long timestamp) {
        this.username = username;
        this.text = comment;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
