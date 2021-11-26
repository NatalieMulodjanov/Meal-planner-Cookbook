package com.example.meal_planner_cookbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RecipeAPI {
    String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";

    @Headers("x-rapidapi-key:" + "31e07079c9mshc35c49be1404043p15a716jsn30a68abea982")
    @GET("/recipes/random?number=10")
    Call<GetRandomRecipesResponse> getRandomRecipes();

}

