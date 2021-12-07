package com.example.meal_planner_cookbook;

public class Result {
    private long id;
    private String title;
    private String image;

    public Result(long id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
