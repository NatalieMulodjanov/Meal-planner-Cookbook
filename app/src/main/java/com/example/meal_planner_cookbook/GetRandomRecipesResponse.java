package com.example.meal_planner_cookbook;

import java.util.List;

public class GetRandomRecipesResponse implements ApiResults{
    private List<Recipe> recipes;

    public GetRandomRecipesResponse(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
