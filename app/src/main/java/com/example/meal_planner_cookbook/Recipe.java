package com.example.meal_planner_cookbook;

import java.util.List;

public class Recipe {
    private boolean vegetarian;
    private boolean vegan;
    private boolean ketogenic;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean cheap;
    private int servings;
    private int preparationMinutes;
    private int cookingMinutes;
    private List<Ingredient> extendedIngredients;
    private long id;
    private String title;
    private String image;
    private String instructions;
    private String sourceUrl;

    public Recipe() {}

    public Recipe(boolean vegetarian, boolean vegan, boolean ketogenic, boolean glutenFree, boolean dairyFree, boolean cheap, int servings, int preparationMinutes, int cookingMinutes, List<Ingredient> extendedIngredients, long id, String title, String image, String instructions) {
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.ketogenic = ketogenic;
        this.glutenFree = glutenFree;
        this.dairyFree = dairyFree;
        this.cheap = cheap;
        this.servings = servings;
        this.preparationMinutes = preparationMinutes;
        this.cookingMinutes = cookingMinutes;
        this.extendedIngredients = extendedIngredients;
        this.id = id;
        this.title = title;
        this.image = image;
        this.instructions = instructions;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public void setKetogenic(boolean ketogenic) {
        this.ketogenic = ketogenic;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public boolean isCheap() {
        return cheap;
    }

    public void setCheap(boolean cheap) {
        this.cheap = cheap;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public double getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    public double getCookingMinutes() {
        return cookingMinutes;
    }

    public void setCookingMinutes(int cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
    }

    public List<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<Ingredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
