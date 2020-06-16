package com.google.sps.servlets;

/** Comment describes comment posted on the portfolio website including username and published date**/
public final class Comment {
    private final long id;
    private final String username;
    private final String text;
    private final long timestamp;

    public Comment(long id, String username, String comment, long timestamp) {
        this.id = id;
        this.username = username;
        this.text = comment;
        this.timestamp = timestamp;
    }

    public Comment(String username, String comment, long timestamp) {
        this.id = -1;
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
