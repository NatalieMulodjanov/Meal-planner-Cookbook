package com.example.meal_planner_cookbook;

import java.util.Objects;

public class Comment {
    private String username;
    private String comment;
    private String timestamp;

    public Comment() {}

    public Comment(String username, String comment, String timestamp) {
        this.username = username;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return username.equals(comment1.username) && comment.equals(comment1.comment) && timestamp.equals(comment1.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, comment, timestamp);
    }
}
