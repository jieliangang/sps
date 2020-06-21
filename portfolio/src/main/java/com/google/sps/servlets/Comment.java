package com.google.sps.servlets;

/** Comment describes comment posted on the portfolio website including username and published date**/
public final class Comment {
    private final long id;
    private final String username;
    private final String text;
    private final long timestamp;
    private final String imageUrl;

    public Comment(long id, String username, String comment, long timestamp, String imageUrl) {
        this.id = id;
        this.username = username;
        this.text = comment;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
    }

    public Comment(String username, String comment, long timestamp, String imageUrl) {
        this.id = -1;
        this.username = username;
        this.text = comment;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    /** Check if comment is valid. Valid when username is present and contains at least text or image. */
    public boolean isValid() {
        return username.trim().length() > 0 &&
                (text.trim().length() > 0 || !imageUrl.isEmpty());
    }
}
