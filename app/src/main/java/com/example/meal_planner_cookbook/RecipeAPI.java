package com.example.meal_planner_cookbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeAPI {
    String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";

    @Headers("x-rapidapi-key:" + "")//natalie's api key
    //@Headers("x-rapidapi-key:" + "")//costa's api key
    @GET("/recipes/random?number=10")
    Call<GetRandomRecipesResponse> getRandomRecipes();

    @Headers("x-rapidapi-key:" + "")//natalie's api key
//    @Headers("x-rapidapi-key:" + "")//costa's api key
    @GET("/recipes/search")
    Call<GetSearchResultsResponse> getSearchResults(@Query("query") String query);

    @Headers("x-rapidapi-key:" + "")//natalie's api key
//    @Headers("x-rapidapi-key:" + "")//costa's api key
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeById(@Path("id") long id);

}

