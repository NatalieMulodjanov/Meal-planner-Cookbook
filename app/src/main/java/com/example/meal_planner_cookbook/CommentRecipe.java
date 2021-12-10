package com.example.meal_planner_cookbook;

import java.util.List;

public class CommentRecipe {
    private String recipeId;
    private List<Comment> comments;

    public CommentRecipe(String recipeId, List<Comment> comments) {
        this.recipeId = recipeId;
        this.comments = comments;
    }

    public CommentRecipe() {}

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
